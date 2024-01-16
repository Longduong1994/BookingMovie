package booking_movie.repository;

import booking_movie.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author huyqt97
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByCategoryName(String s);
    @Query("SELECT C from Category C where C.isDelete = : aBoolean AND LOWER(C.categoryName) LIKE lower(CONCAT('%', :search, '%'))")
    Page<Category> findAllByIsDeleteAndCategoryName(Boolean aBoolean, String search, PageRequest pageRequest);
}
