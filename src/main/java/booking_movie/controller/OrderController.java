package booking_movie.controller;

import booking_movie.dto.request.MenuRequestDto;
import booking_movie.dto.request.OrderRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.OrderException;
import booking_movie.service.menu.MenuService;
import booking_movie.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MenuService menuService;

    @PostMapping
    private ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto, Authentication authentication) throws CustomsException, LoginException, NotFoundException {
        return new ResponseEntity<>(orderService.create(authentication,orderRequestDto), HttpStatus.OK);
    }


    @PostMapping("/createMenu")
    public ResponseEntity<?> createMenu(@Valid @RequestBody List<MenuRequestDto> listMenuRequestDto,  @RequestParam Long orderId) {
        try {
            String response = menuService.push(listMenuRequestDto, orderId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable String id) throws NotFoundException {
        Long orderId = Long.parseLong(id);
        return new ResponseEntity<>(orderService.findByOrderId(orderId),HttpStatus.OK);
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<?> getMenu(@PathVariable String id) throws NotFoundException, OrderException {
        Long orderId = Long.parseLong(id);
        return new ResponseEntity<>(menuService.findAllByOrder(orderId),HttpStatus.OK);
    }

}
