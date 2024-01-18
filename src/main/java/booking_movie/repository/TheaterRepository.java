package booking_movie.repository;

import booking_movie.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Page<Theater> findAllByTheaterNameContainingIgnoreCaseAndIsDeletedFalse(String search , Pageable pageable) ;
    Page<Theater> findAllByIsDeletedFalse( Pageable pageable) ;

    List<Theater> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    List<Theater> findAllByLocationIsDeleteTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    Boolean existsByTheaterName(String name) ;

}
