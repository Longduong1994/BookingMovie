package booking_movie.controller;

import booking_movie.dto.request.MenuRequestDto;
import booking_movie.dto.request.OrderRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.UserException;
import booking_movie.exception.OrderException;
import booking_movie.service.menu.MenuService;
import booking_movie.service.order.OrderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
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

    @GetMapping("/oder-customer")
    private ResponseEntity<?> totalUser(Authentication authentication) throws UserException {
        return new ResponseEntity<>(orderService.sumTotalSpending(authentication),HttpStatus.OK);
    }
    @GetMapping("/history-customer")
    private ResponseEntity<?> findAllByUser(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "6") Integer size,Authentication authentication) throws UserException {
    return new ResponseEntity<>(orderService.findAllByUser(page,size,authentication),HttpStatus.OK);
    }
//    find All or oll date
    @GetMapping
    private ResponseEntity<?> getAll(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "6") Integer size){
        if(localDate == null){
            return new ResponseEntity<>(orderService.findAll(page,size),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(orderService.findAllLocalDate(page,size,localDate),HttpStatus.OK);
        }
    }
    
//  tổng danh thu hệ thống
    @GetMapping("/total")
    private ResponseEntity<?> totalRevenue(Authentication authentication) throws UserException {
        return new ResponseEntity<>(orderService.sumTotalRevenue(authentication),HttpStatus.OK);
    }


//    tổng danh thu theo năm hiện tại
    @GetMapping("/total-current-year")
    private ResponseEntity<?> totalCurrentYear(){
        return new ResponseEntity<>(orderService.sumTotalCurrentYear(),HttpStatus.OK);
    };


    @GetMapping("/spending")
    public ResponseEntity<?> spending(Authentication authentication,@RequestParam(defaultValue = "2024") String year) throws LoginException {
        Long yearLong = Long.parseLong(year);
        return new ResponseEntity<>(orderService.spendingByYear(yearLong,authentication),HttpStatus.OK);
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

    @GetMapping("/sumYear")
    private ResponseEntity<?> getSumYear(Authentication authentication) throws UserException {
        return new ResponseEntity<>(orderService.sumTotalCurrentYear(authentication),HttpStatus.OK);
    }

}
