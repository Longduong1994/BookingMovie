package booking_movie.service.category;

import booking_movie.dto.request.category.CategoryRequestDto;
import booking_movie.dto.request.category.CategoryUpdateRequestDto;
import booking_movie.entity.Category;
import booking_movie.exception.CategoryException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface CategoryService {
    Category create(CategoryRequestDto categoryRequestDto, Authentication authentication) throws CategoryException;
    Category update(CategoryUpdateRequestDto categoryUpdateRequestDto, Authentication authentication) throws CategoryException;
    Page<Category> findAll(int page, int size, String search);
    String delete(Long id, Authentication authentication) throws CategoryException;
}
