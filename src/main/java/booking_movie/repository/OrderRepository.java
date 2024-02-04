package booking_movie.repository;


import booking_movie.constants.Status;
import booking_movie.entity.Movie;
import booking_movie.entity.Order;
import booking_movie.entity.Theater;
import booking_movie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserAndStatus(User user, Status status);

    Optional<Order> findByCode(String code);

    @Query("SELECT sum(O.total)FROM Order O JOIN O.user User  where User.id =:user_id AND year (O.createTime)=:year ")
    Double getTotal(@Param("user_id") Long userId,@Param("year") Long year);

    @Query("SELECT SUM(O.total) FROM Order O WHERE O.user = :user AND YEAR(O.createTime) = YEAR(CURRENT_DATE)")
    Double getTotalUser(@Param("user") User user);
    Page<Order> findAllByUser(User user, Pageable pageable);
    Page<Order> findAllByBookingDate(LocalDate localDate, Pageable pageable);
    @Query("SELECT SUM(O.total) FROM Order O")
    Double sumTotalRevenue();
    @Query("SELECT SUM(o.total) FROM Order o WHERE YEAR(o.bookingDate) = :year")
    Double sumTotalYear(@Param("year") Integer year);
<<<<<<< HEAD
    @Query("SELECT O FROM Order O JOIN O.user U WHERE " +
            "(:searchUser IS NULL OR :searchUser = '' OR U.username LIKE %:searchUser%) " +
            "AND (:searchYear IS NULL OR :searchYear = 0 OR YEAR(O.bookingDate) = :searchYear) " +
            "AND (:searchMovie IS NULL OR :searchMovie = '' OR O.movieName = :searchMovie) " +
            "AND (:searchTheater IS NULL OR :searchTheater = '' OR O.theaterName = :searchTheater)")
    Page<Order> findAllInAdmin(@Param("searchUser") String searchUser,
                               @Param("searchYear") Integer searchYear,
                               @Param("searchMovie") String searchMovie,
                               @Param("searchTheater") String searchTheater,
                               Pageable pageable);
=======

    @Query(value = "SELECT COALESCE(SUM(o.total), 0) FROM booking_movie o WHERE o.user_id = ?1", nativeQuery = true)
    Double getTotalSumByUserId(Long userId);

    @Query(value = "SELECT COALESCE(SUM(o.total), 0) FROM booking_movie o WHERE o.user_id = ?1 AND YEAR(o.create_time) = ?2", nativeQuery = true)
    Long getTotalSumByUserIdAndYear(Long userId, Long year);

>>>>>>> 116aaf4689d43e8f169e92d8f212b39f660b0ede
}
