package booking_movie.service.coupon;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.dto.request.UserId;
import booking_movie.dto.response.CouponResponseDto;
import booking_movie.entity.Coupon;
import booking_movie.entity.Notification;
import booking_movie.entity.User;
import booking_movie.exception.LoginException;
import booking_movie.exception.UserException;
import booking_movie.exception.NotFoundException;
import booking_movie.repository.CouponRepository;
import booking_movie.repository.UserRepository;
import booking_movie.security.user_principle.UserPrincipal;
import booking_movie.service.notification.NotificationService;
import booking_movie.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final UserService userService;

    @Override
    public Double checkCoupon(String code) throws NotFoundException {
        Optional<Coupon> coupon = couponRepository.findByCode(code);
        if (coupon.isEmpty()){
            throw new NotFoundException("Hhông tìm thấy mã.");
        }
        if (coupon.get().getStatus()){
            throw new NotFoundException("Mã đã sử dụng.");
        }
        LocalDate endDate = coupon.get().getEndDate();
        if (endDate.isBefore(LocalDate.now())) {
            throw new NotFoundException("Mã hết hạn sử dụng.");
        }
        coupon.get().setStatus(true);
        couponRepository.save(coupon.get());
            return coupon.get().getSalePrice();
    }

    /**
     * search coupon by code
     *
     * @author huyqt97
     */
    @Override
    public String create(CouponRequestDto couponRequestDto, Authentication authentication) throws Exception {
        String codeNew = randomCode(couponRequestDto,authentication);
        Set<User> users = new HashSet<>();
        for (UserId id : couponRequestDto.getListUserId()) {
        Optional<User> user = userRepository.findById(id.getId());
                Coupon coupon = Coupon.builder().code(codeNew)
                        .description(couponRequestDto.getDescription())
                        .endDate(couponRequestDto.getEffectDate())
                        .salePrice(couponRequestDto.getSalePrice())
                        .isDelete(false)
                        .user(user.orElseThrow(()-> new Exception("Người dùng " + id.getId())))
                        .status(true).build();
                couponRepository.save(coupon);
        users.add(user.get());
        }
        String message ="\"Bạn nhận được 1 voucher giá trị \"+couponRequestDto.getSalePrice() +\"." +
                " Có hiệu lực từ ngày "+ LocalDate.now()+" đến "+couponRequestDto.getEffectDate()+" ." +
                " Mã của bạn là :"+codeNew +" ."+
                " Chương trình áp lục cho tất cả các phim . Chào bạn !!!\"";


        Notification notification = new Notification();
        notification.setTitle(couponRequestDto.getDescription());
        notification.setMessage(message);
        notification.setCreatedAt(LocalDate.now());
        notification.setRead(false);
        notification.setUsers(users);
        notificationService.create(notification);
        return "Thành công!";
    }

    /**
     * random code
     *
     * @author huyqt97
     */
    public String randomCode(CouponRequestDto couponRequestDto, Authentication authentication) throws Exception {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            str.append(digit);
        }
        if(couponRepository.findByCode(str.toString()).isPresent()){
            create(couponRequestDto,authentication);
        }
        return str.toString();
    }

    /**
     * find all by User
     *
     * @author huyqt97
     */
    @Override
    public List<CouponResponseDto> findAllByUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        List<CouponResponseDto> couponList = new ArrayList<>();
        if(user!= null){
            List<Coupon> userCoupons = couponRepository.findAllByUserAndStatus(user,false);
            for (int i = userCoupons.size() - 1; i >= 0; i--) {
                Coupon c = userCoupons.get(i);
                CouponResponseDto couponResponseDto = mapper(c);
                couponList.add(couponResponseDto);
            }
        }
        return couponList;
    }

    @Override
    public List<CouponResponseDto> findAllByUserAndStatus(Authentication authentication) throws LoginException {
        User user = userService.getUser(authentication);
        LocalDate endDate = LocalDate.now();
        return couponRepository.findByUserAndStatusAndEndDate(user.getId(),endDate).stream().map(c->mapper(c)).collect(Collectors.toList());
    }

    /**
     * convert Coupon into CouponResponseDto
     *
     * @author huyqt97
     */
    public CouponResponseDto mapper(Coupon c){
        return CouponResponseDto.builder()
                .id(c.getId())
                .code(c.getCode())
                .description(c.getDescription())
                .endDate(c.getEndDate())
                .user(c.getUser().getUsername())
                .sale(c.getSalePrice())
                .status(c.getStatus())
                .user(c.getUser().getUsername())
                .isDelete(c.getIsDelete()).build();
    }

    @Override
    public Coupon updateStatus(Long id, Authentication authentication) throws UserException, NotFoundException {
        User user = userService.userById(authentication);
        return couponRepository.findByIdAndUserAndStatus(id,user,true).orElseThrow(()-> new NotFoundException("coupon not found"));
    }

    @Override
    public List<Coupon> showCoupon(Authentication authentication, Long id) throws UserException {
        userService.userById(authentication);
        User user = userRepository.findById(id).orElseThrow(()-> new UserException("Người cần tìm không tồn tại!"));
        return couponRepository.findAllByUserAndStatus(user,true);
    }
}
