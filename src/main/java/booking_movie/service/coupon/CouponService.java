package booking_movie.service.coupon;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.dto.response.CouponResponseDto;
import booking_movie.entity.Coupon;
import booking_movie.exception.LoginException;
import booking_movie.exception.UserException;
import booking_movie.exception.NotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CouponService {
    /**
     * create coupon
     *
     * @author huyqt97
     */
    /* Todo 12/01/2024 */
    String create(CouponRequestDto couponRequestDto, Authentication authentication) throws Exception;
    /**
     * List coupon by user
     *
     * @author huyqt97
     */
    /* Todo 12/01/2024 */
    List<CouponResponseDto> findAllByUser(Authentication authentication);


    List<CouponResponseDto> findAllByUserAndStatus(Authentication authentication) throws LoginException;


    Coupon updateStatus(Long id, Authentication authentication) throws UserException, NotFoundException;

    /**
     *
     */

<<<<<<< HEAD
    String checkCoupon(String code) throws NotFoundException;
    List<Coupon> showCoupon(Authentication authentication, Long id) throws UserException;
=======
    Double checkCoupon(String code) throws NotFoundException;
>>>>>>> 116aaf4689d43e8f169e92d8f212b39f660b0ede
}
