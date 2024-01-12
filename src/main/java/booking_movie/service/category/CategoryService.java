package booking_movie.service.category;

import booking_movie.dto.request.category.CategoryRequestDto;
import booking_movie.dto.request.category.CategoryUpdateRequestDto;
import booking_movie.entity.Category;
import booking_movie.exception.CategoryException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface CategoryService {
    /**
     * create new category
     *
     * @author huyqt97
     */
    Category create(CategoryRequestDto categoryRequestDto, Authentication authentication) throws CategoryException;
    /**
     * update category
     *
     * @author huyqt97
     */
    Category update(CategoryUpdateRequestDto categoryUpdateRequestDto, Authentication authentication) throws CategoryException;
    /**
     * find all category
     *
     * @author huyt97
     */
    Page<Category> findAll(int page, int size, String search);

    /**
     * delete by id
     *
     * @author huyt97
     */
    String delete(Long id,Authentication  authentication) throws CategoryException;
}
