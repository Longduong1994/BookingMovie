package booking_movie.service.dish;

import booking_movie.dto.request.dish.DishRequestDto;
import booking_movie.dto.request.dish.DishUpdateRequestDto;
import booking_movie.entity.Dish;
import booking_movie.exception.DishException;
import booking_movie.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface DishService {
    Dish create(DishRequestDto dishRequestDto, Authentication authentication) throws DishException, UserException;
    Dish update(DishUpdateRequestDto dishUpdateRequestDto,Authentication authentication) throws DishException, UserException;
    Page<Dish> findAll(int page, int size, String search,Authentication authentication) throws DishException, UserException;
    String delete(Long id,Authentication authentication) throws DishException, UserException;
    Dish findById(Long id)throws DishException;
}
