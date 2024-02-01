package booking_movie.repository;


import booking_movie.constants.Status;
import booking_movie.entity.Order;
import booking_movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserAndStatus(User user, Status status);

    @Query("SELECT sum(O.total)FROM Order O JOIN O.user User  where User.id =:user_id AND year (O.createTime)=:year ")
    Double getTotal(@Param("user_id") Long userId,@Param("year") Long year);

    @Query("SELECT SUM(O.total) FROM Order O WHERE O.user = :user AND YEAR(O.createTime) = YEAR(CURRENT_DATE)")
    Double getTotalUser(@Param("user") User user);
}
