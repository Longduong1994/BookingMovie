package booking_movie.service.order;

import booking_movie.dto.request.OrderRequestDto;

import booking_movie.dto.response.OrderResponseDto;
import booking_movie.entity.Order;
import booking_movie.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponseDto create(Authentication authentication, OrderRequestDto orderRequestDto) throws LoginException, CustomsException, NotFoundException;

    Order findByCode(String code);
    OrderResponseDto findByOrderId(Long id) throws NotFoundException;

    void deleteOrder(Long id);

    //     Order create(OrderRequestDto orderRequestDto, Authentication authentication) throws OrderException, UserException;
//     Order update(Authentication authentication) throws OrderException, UserException;

     Order findById(Long id)throws OrderException;
     Double sumTotalSpending(Authentication authentication) throws UserException;
     Page<Order> findAllByUser(Integer page,Integer size,Authentication authentication) throws UserException;
     Page<Order> findAllLocalDate(Integer page, Integer size, LocalDate localDate);
     Page<Order> findAll(Integer page, Integer size);
     Double sumTotalRevenue(Authentication authentication) throws UserException;
     Double sumTotalCurrentYear(Authentication authentication) throws UserException;
}
