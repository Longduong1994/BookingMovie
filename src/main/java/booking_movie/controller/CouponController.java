package booking_movie.controller;

import booking_movie.dto.request.CouponRequestDto;
import booking_movie.entity.Coupon;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.UserException;
import booking_movie.service.coupon.CouponService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking/v1/coupon")
@AllArgsConstructor
public class CouponController {
    private CouponService couponService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllByUserAndStatus(Authentication authentication) throws LoginException {
        return new ResponseEntity<>(couponService.findAllByUserAndStatus(authentication),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllByUser(Authentication authentication){
        return new ResponseEntity<>(couponService.findAllByUser(authentication), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addCoupon(Authentication authentication,@Valid @RequestBody CouponRequestDto couponRequestDto) throws Exception {
        System.out.println(couponRequestDto);
        return new ResponseEntity<>(couponService.create(couponRequestDto,authentication),HttpStatus.OK);
    }
    @PatchMapping("{id}")
    public  ResponseEntity<?> useCoupon(@PathVariable("id") Long id,Authentication authentication) throws UserException, NotFoundException {
        return new ResponseEntity<>(couponService.updateStatus(id, authentication), HttpStatus.OK);
    }
<<<<<<< HEAD
    @GetMapping("/show-customer/{id}")
    private ResponseEntity<?> get(Authentication authentication,@PathVariable("id") Long id) throws UserException {
        return new ResponseEntity<>(couponService.showCoupon(authentication,id),HttpStatus.OK);
=======

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam String code) throws NotFoundException {
        return new ResponseEntity<>(couponService.checkCoupon(code),HttpStatus.OK);
>>>>>>> 116aaf4689d43e8f169e92d8f212b39f660b0ede
    }
}
