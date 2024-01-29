package booking_movie.service.category;

import booking_movie.constants.RoleName;
import booking_movie.dto.request.category.CategoryRequestDto;
import booking_movie.dto.request.category.CategoryUpdateRequestDto;
import booking_movie.entity.Category;
import booking_movie.entity.User;
import booking_movie.exception.CategoryException;
import booking_movie.exception.DishException;
import booking_movie.exception.UserException;
import booking_movie.mapper.CategoryMapper;
import booking_movie.repository.CategoryRepository;
import booking_movie.security.user_principle.UserPrincipal;
import booking_movie.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService  userService;

    /**
     * create new category
     *
     * @author huyqt97
     */
    @Override
    public Category create(CategoryRequestDto categoryRequestDto, Authentication authentication) throws CategoryException, UserException {
        User user = userService.userById(authentication);
        if (categoryRepository.existsByCategoryName(categoryRequestDto.getCategoryName())) {
            throw new CategoryException("CategoryName already exist");
        }

        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if (isAdminOrManager) {
            Category category = categoryMapper.categoryRequestDtoIntoCategory(categoryRequestDto,user.getUsername());
            category.setTheater(user.getTheater());
            return categoryRepository.save(category);
        }
        throw new CategoryException("No permissions granted");
    }

    /**
     * update category
     *
     * @author huyqt97
     */
    @Override
    public Category update(CategoryUpdateRequestDto categoryUpdateRequestDto, Authentication authentication) throws CategoryException, UserException {
        User user= userService.userById(authentication);
        if (categoryRepository.existsByCategoryName(categoryUpdateRequestDto.getCategoryName())) {
            throw new CategoryException("CategoryName already exist");
        }
        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if (isAdminOrManager) {
            Category category = categoryMapper.categoryUpdateRequestDtoIntoCategory(categoryUpdateRequestDto,user.getUsername());
            return categoryRepository.save(category);
        }
        throw new CategoryException("no permissions granted");
    }

    /**
     * find all category
     *
     * @author huyqt97
     */
    @Override
    public List<Category> findAll() throws CategoryException, UserException {
        return categoryRepository.findAll();
    }

    /**
     * delete by id
     *
     * @author huyqt97
     */
    @Override
    public String delete(Long id, Authentication authentication) throws CategoryException, UserException {
        User user = userService.userById(authentication);
        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if (isAdminOrManager) {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                Category c = category.get();
                c.setIsDelete(true);
                c.setUpdateTime(LocalDate.now());
                c.setUpdateUser(user.getUsername());
                categoryRepository.save(c);
                return "Delete success";
            }
        }
        throw new CategoryException("no permissions granted");
    }

}