package booking_movie.controller;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.UserException;
import booking_movie.service.coupon.CouponService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/coupon")
@AllArgsConstructor
public class CouponController {
    private CouponService couponService;
    @GetMapping
    public ResponseEntity<?> getAllByUser(Authentication authentication){
        return new ResponseEntity<>(couponService.findAllByUser(authentication), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addCoupon(Authentication authentication,@Valid @RequestBody CouponRequestDto couponRequestDto) throws Exception {
        return new ResponseEntity<>(couponService.create(couponRequestDto,authentication),HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public  ResponseEntity<?> useCoupon(@PathVariable("id") Long id,Authentication authentication) throws UserException, NotFoundException {
        return new ResponseEntity<>(couponService.updateStatus(id, authentication), HttpStatus.OK);
    }
}
