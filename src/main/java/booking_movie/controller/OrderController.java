package booking_movie.controller;

import booking_movie.dto.request.OrderRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    private ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto, Authentication authentication) throws CustomsException, LoginException, NotFoundException {
        return new ResponseEntity<>(orderService.create(authentication,orderRequestDto), HttpStatus.OK);
    }
}
