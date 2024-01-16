package booking_movie.repository;

import booking_movie.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author huyqt97
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Boolean existsByCategoryName(String s);
    @Query("SELECT C FROM Category C WHERE C.isDelete = :aBoolean AND LOWER(C.categoryName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Category> findAllByIsDeleteAndCategoryName(@Param("aBoolean") Boolean aBoolean, @Param("search") String search, PageRequest pageRequest);
}
