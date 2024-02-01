package booking_movie.repository;

import booking_movie.entity.Coupon;
import booking_movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    /**
     * search Coupon by code
     *
     * @author huyqt97
     */
    Optional<Coupon> findByCode(String code);
    /**
     * find all by user
     *
     * @author huyqt97
     */
    List<Coupon> findAllByUser(User user);

    Optional<Coupon> findByIdAndUser(Long id,User user);
}