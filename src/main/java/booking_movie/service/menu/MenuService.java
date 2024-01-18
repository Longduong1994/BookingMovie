package booking_movie.service.menu;

import booking_movie.dto.request.MenuRequestDto;
import booking_movie.dto.response.MenuResponseDto;
import booking_movie.entity.Menu;
import booking_movie.entity.Order;
import booking_movie.exception.DishException;
import booking_movie.exception.MenuException;
import booking_movie.exception.OrderException;
import booking_movie.exception.UserException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MenuService {
    String push(MenuRequestDto menuRequestDto,Authentication authentication) throws UserException, DishException, OrderException, MenuException;
    String sell(MenuRequestDto menuRequestDto,Authentication authentication) throws UserException, DishException, OrderException, MenuException;
    Menu findById(Long id) throws MenuException;
    List<MenuResponseDto> findAllByOrder(Long orderId) throws OrderException;
}
