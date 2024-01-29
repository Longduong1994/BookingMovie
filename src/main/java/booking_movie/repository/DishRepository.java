package booking_movie.repository;

import booking_movie.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Boolean existsByDishName(String name);
    Page<Dish> findAllByIsDeleteAndTheater(Boolean aBoolean,Long theater,Pageable pageable);
    Page<Dish> findAllByIsDeleteAndTheaterAndDishNameContainingIgnoreCase(Boolean isDelete,Long theater,Pageable pageable,String search);
    Boolean existsByTheater(Long id);
}
