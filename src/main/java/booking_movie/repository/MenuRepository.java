package booking_movie.repository;

import booking_movie.entity.Menu;
import booking_movie.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findAllByOrder(Order order);
}
