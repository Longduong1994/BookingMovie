package booking_movie.repository;

import booking_movie.constants.Status;
import booking_movie.entity.Order;
import booking_movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserAndStatus(User user, Status status);
}
