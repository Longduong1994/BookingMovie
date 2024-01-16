package booking_movie.mapper;

import booking_movie.dto.request.category.CategoryRequestDto;
import booking_movie.dto.request.category.CategoryUpdateRequestDto;
import booking_movie.entity.Category;
import booking_movie.exception.CategoryException;
import booking_movie.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryMapper {
    private final CategoryRepository categoryRepository;

    public Category categoryRequestDtoIntoCategory(CategoryRequestDto categoryRequestDto, String userName) {
        return Category.builder()
                .categoryName(categoryRequestDto.getCategoryName())
                .createUser(userName)
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .isDelete(false)
                .build();
    }

    public Category categoryUpdateRequestDtoIntoCategory(CategoryUpdateRequestDto categoryUpdateRequestDto, String username) throws CategoryException {
        Optional<Category> category = categoryRepository.findById(categoryUpdateRequestDto.getId());
        if (category.isPresent()) {
            Category category1 = category.get();
            category1.setUpdateTime(LocalDate.now());
            category1.setUpdateUser(username);
            category1.setCategoryName(categoryUpdateRequestDto.getCategoryName());
            return category1;
        }
        throw new CategoryException("CategoryId not found");
    }
}
