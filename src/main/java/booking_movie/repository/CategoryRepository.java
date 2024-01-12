package booking_movie.repository;

import booking_movie.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huyqt97
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByCategoryName(String s);
    Page<Category> findAllByIsDeletedAndCategoryName(Boolean aBoolean, String search, PageRequest pageRequest);
}
