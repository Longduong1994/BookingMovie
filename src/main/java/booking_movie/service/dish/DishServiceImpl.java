package booking_movie.service.dish;

import booking_movie.constants.RoleName;
import booking_movie.dto.request.dish.DishRequestDto;
import booking_movie.dto.request.dish.DishUpdateRequestDto;
import booking_movie.entity.Dish;
import booking_movie.entity.User;
import booking_movie.exception.DishException;
import booking_movie.exception.UserException;
import booking_movie.mapper.DishMapper;
import booking_movie.repository.CategoryRepository;
import booking_movie.repository.DishRepository;
import booking_movie.service.user.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final DishMapper dishMapper;
    private final UserService userService;

    /**
     * Create dish
     *
     * @author huyqt97
     */
    @Override
    public Dish create(DishRequestDto dishRequestDto, Authentication authentication) throws DishException, UserException {
        User user = userService.userById(authentication);
        if (dishRepository.existsByDishName(dishRequestDto.getDishName())) {
            throw new DishException("Tên sản phầm đã tồn tại");
        }

        if (categoryRepository.findById(dishRequestDto.getCategoryId()).isEmpty()) {
            throw new DishException("Không tồn tại danh mục");
        }
        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if (isAdminOrManager) {
            Dish dish = dishMapper.dishRequestDtoIntoDish(dishRequestDto, user.getUsername());
            dish.setTheater(user.getTheater());
            return dishRepository.save(dish);
        }
        throw new DishException("Không có quyền nào được cấp");
    }

    /**
     * Update dish
     *
     * @author huyqt97
     */

    @Override
    public Dish update(DishUpdateRequestDto dishUpdateRequestDto, Authentication authentication) throws DishException, UserException {

        User user = userService.userById(authentication);

        if (dishRepository.existsByDishName(dishUpdateRequestDto.getDishName())) {
            throw new DishException("Tên sản phẩm đã tồn tại");
        }

        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if(dishRepository.existsByTheater(user.getTheater())) {
          if (isAdminOrManager) {
            return dishRepository.save(dishMapper.dishUpdateRequestDtoInto(dishUpdateRequestDto, user.getUsername()));
          }
           throw new DishException("No permissions granted");
        }
        throw new DishException("Không có quyền nào được cấp");

    }

    /**
     * find all by status
     *
     * @author huyqt97
     */
    @Override
    public Page<Dish> findAll(int page, int size, String search,Authentication authentication) throws DishException, UserException {
        User user = userService.userById(authentication);
        if(search.equals("")){
            return dishRepository.findAllByIsDeleteAndTheater(false,user.getTheater(),PageRequest.of(page,size));
        }
        return dishRepository.findAllByIsDeleteAndTheaterAndDishNameContainingIgnoreCase(false,user.getTheater(), PageRequest.of(page,size),search);
    }

    /**
     * delete by ID
     *
     * @author huyqt97
     */
    @Override
    public String delete(Long id,Authentication authentication) throws DishException, UserException {
        User user = userService.userById(authentication);
            Dish dish1 = findById(id);
            dish1.setIsDelete(true);
            dish1.setUpdateTime(LocalDate.now());
            dish1.setUpdateUser(user.getUsername());
            dishRepository.save(dish1);
            return "Xoá thành công";
    }

    /**
     * find by id
     *
     * @author huyqt97
     */
    @Override
    public Dish findById(Long id) throws DishException {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent()){
            return dish.get();
        }throw new DishException("Sản phẩm không tồn tại");
    }

    @Override
    public List<Dish> findAllList() {
        return dishRepository.findAll();
    }
}
