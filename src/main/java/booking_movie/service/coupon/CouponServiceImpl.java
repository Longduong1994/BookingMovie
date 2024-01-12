package booking_movie.service.coupon;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.dto.response.CouponResponseDto;
import booking_movie.entity.Coupon;
import booking_movie.entity.User;
import booking_movie.repository.CouponRepository;
import booking_movie.repository.UserRepository;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

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
            throw new Exception("Not found");
        }
        Coupon coupon = Coupon.builder().code(codeNew)
                .description(couponRequestDto.getDescription())
                .endDate(LocalDate.now())
                .isDelete(false)
                .user(user.get())
                .status(true).build();
        Coupon c = couponRepository.save(coupon);
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
