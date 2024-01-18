package booking_movie.repository;

import booking_movie.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Boolean existsByDishName(String name);
    @Query("SELECT D FROM Dish D LEFT JOIN D.category C WHERE D.isDelete = :isDeleted AND D.theater = :theater AND (LOWER(D.dishName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(C.categoryName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Dish> findAllByIsDeleteAndTheater(@Param("isDeleted") Boolean isDeleted, @Param("theater") Long theater, @Param("search") String search, Pageable pageable);

}
