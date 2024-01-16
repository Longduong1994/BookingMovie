package booking_movie.service.dish;

import booking_movie.constants.RoleName;
import booking_movie.dto.request.dish.DishRequestDto;
import booking_movie.dto.request.dish.DishUpdateRequestDto;
import booking_movie.entity.Dish;
import booking_movie.entity.User;
import booking_movie.exception.DishException;
import booking_movie.mapper.DishMapper;
import booking_movie.repository.CategoryRepository;
import booking_movie.repository.DishRepository;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final DishMapper dishMapper;

    /**
     * Create dish
     *
     * @author huyqt97
     */
    @Override
    public Dish create(DishRequestDto dishRequestDto, Authentication authentication) throws DishException {
        User user = userById(authentication);
        if (dishRepository.existsByDishName(dishRequestDto.getDishName())) {
            throw new DishException("Dish is exist");
        }

        if (categoryRepository.findById(dishRequestDto.getCategoryId()).isEmpty()) {
            throw new DishException("Category not found");
        }
        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if (isAdminOrManager) {
            return dishRepository.save(dishMapper.dishRequestDtoIntoDish(dishRequestDto, user.getUsername()));
        }
        throw new DishException("No permissions granted");
    }

    /**
     * Update dish
     *
     * @author huyqt97
     */

    @Override
    public Dish update(DishUpdateRequestDto dishUpdateRequestDto, Authentication authentication) throws DishException {

        User user = userById(authentication);

        if (dishRepository.existsByDishName(dishUpdateRequestDto.getDishName())) {
            throw new DishException("Dish is exist");
        }

        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));

        if (isAdminOrManager) {
            return dishRepository.save(dishMapper.dishUpdateRequestDtoInto(dishUpdateRequestDto, user.getUsername()));
        }
        throw new DishException("No permissions granted");
    }

    /**
     * find all by status
     *
     * @author huyqt97
     */
    @Override
    public Page<Dish> findAll(int page, int size, String search) {
        return dishRepository.findAllByIsDelete(false,search, PageRequest.of(page,size));
    }

    /**
     * delete by ID
     *
     * @author huyqt97
     */
    @Override
    public String delete(Long id,Authentication authentication) throws DishException {
        User user = userById(authentication);
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent()){
            Dish dish1 =dish.get();
            dish1.setIsDelete(true);
            dish1.setUpdateTime(LocalDate.now());
            dish1.setUpdateUser(user.getUsername());
            dishRepository.save(dish1);
        }throw new DishException("Dish not found");
    }

    /**
     * find By id user
     * return {@Link User}
     *
     * @author huyqt97
     */
    public User userById(Authentication authentication)throws DishException{
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (userPrincipal == null || userPrincipal.getUser() == null) {
            throw new DishException("User not found");
        }

        return userPrincipal.getUser();
    }
}
