package booking_movie.service.coupon;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.dto.response.CouponResponseDto;
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
    CouponResponseDto create(CouponRequestDto couponRequestDto, Authentication authentication) throws Exception;
    /**
     * List coupon by user
     *
     * @author huyqt97
     */
    /* Todo 12/01/2024 */
    List<CouponResponseDto> findAllByUser(Authentication authentication);
    /**
     *
     */

    String checkCoupon(String code) throws NotFoundException;

}
