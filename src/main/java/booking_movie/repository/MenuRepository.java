package booking_movie.repository;

import booking_movie.entity.Menu;
import booking_movie.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
        @Query(value = "SELECT m.* FROM booking_menu m WHERE m.booking_movie_id = :orderId", nativeQuery = true)
        List<Menu> findAllByOrder(@Param("orderId") Long orderId);

}
