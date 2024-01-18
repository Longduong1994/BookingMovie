package booking_movie.service.order;

import booking_movie.dto.request.OrderRequestDto;

import booking_movie.dto.response.OrderResponseDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import org.springframework.security.core.Authentication;

public interface OrderService {

    OrderResponseDto create(Authentication authentication, OrderRequestDto orderRequestDto) throws LoginException, CustomsException, NotFoundException;


//     Order create(OrderRequestDto orderRequestDto, Authentication authentication) throws OrderException, UserException;
//     Order update(Authentication authentication) throws OrderException, UserException;
//     Order findById(Long id)throws OrderException;
}
