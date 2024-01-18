package booking_movie.service.order;

import booking_movie.dto.request.OrderRequestDto;
import booking_movie.entity.Order;
import booking_movie.exception.OrderException;
import booking_movie.exception.UserException;
import org.springframework.security.core.Authentication;

public interface OrderService {
    Order create(OrderRequestDto orderRequestDto, Authentication authentication) throws OrderException, UserException;
    Order update(Authentication authentication) throws OrderException, UserException;
    Order findById(Long id)throws OrderException;
}
