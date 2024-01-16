package booking_movie.service.dish;

import booking_movie.dto.request.dish.DishRequestDto;
import booking_movie.dto.request.dish.DishUpdateRequestDto;
import booking_movie.entity.Dish;
import booking_movie.exception.DishException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface DishService {
    Dish create(DishRequestDto dishRequestDto, Authentication authentication) throws DishException;
    Dish update(DishUpdateRequestDto dishUpdateRequestDto,Authentication authentication) throws DishException;
    Page<Dish> findAll(int page, int size, String search);
    String delete(Long id,Authentication authentication) throws DishException;
}
