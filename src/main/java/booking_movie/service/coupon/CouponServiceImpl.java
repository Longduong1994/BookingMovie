package booking_movie.service.coupon;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.dto.response.CouponResponseDto;
import booking_movie.entity.Coupon;
import booking_movie.entity.Notification;
import booking_movie.entity.User;
import booking_movie.repository.CouponRepository;
import booking_movie.repository.UserRepository;
import booking_movie.security.user_principle.UserPrincipal;
import booking_movie.service.notification.NotificationService;
import booking_movie.service.notification.NotificationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    /**
     * search coupon by code
     *
     * @author huyqt97
     */
    @Override
    public CouponResponseDto create(CouponRequestDto couponRequestDto, Authentication authentication) throws Exception {
        String codeNew = randomCode(couponRequestDto,authentication);
        Optional<User> user = userRepository.findById(couponRequestDto.getUser());
        if(user.isEmpty()){
            throw new Exception("Khuyến mãi không tồn tại");
        }
        Coupon coupon = Coupon.builder().code(codeNew)
                .description(couponRequestDto.getDescription())
                .endDate(couponRequestDto.getEffectDate())
                .salePrice(couponRequestDto.getSalePrice())
                .isDelete(false)
                .user(user.get())
                .status(true).build();
        Coupon c = couponRepository.save(coupon);
        String message ="\"Bạn nhận được 1 voucher giá trị \"+couponRequestDto.getSalePrice() +\"." +
                " Có hiệu lực từ ngày "+ LocalDate.now()+" đến "+couponRequestDto.getEffectDate()+" ." +
                " Mã của bạn là :"+codeNew +" ."+
                " Chương trình áp lục cho tất cả các phim . Chào bạn !!!\"";

        Set<User> users = new HashSet<>();
        users.add(user.get());

        Notification notification = new Notification();
        notification.setTitle(couponRequestDto.getDescription());
        notification.setMessage(message);
        notification.setCreatedAt(LocalDate.now());
        notification.setRead(true);
        notification.setUsers(users);
        notificationService.create(notification);

        return mapper(c);
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
            for (Coupon c: couponRepository.findAllByUser(user)) {
                CouponResponseDto couponResponseDto = mapper(c);
                couponList.add(couponResponseDto);
            }
        }
        return couponList;
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
                .status(c.getStatus())
                .isDelete(c.getIsDelete()).build();
    }
}
