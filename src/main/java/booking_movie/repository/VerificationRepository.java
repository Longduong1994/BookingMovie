package booking_movie.repository;

import booking_movie.entity.User;
import booking_movie.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRepository extends JpaRepository<Verification,Long> {
    List<Verification> findByUser(User user);

    Verification findByVerificationCode(String code);
}
