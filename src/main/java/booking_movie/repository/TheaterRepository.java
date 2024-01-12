package booking_movie.repository;

import booking_movie.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Page<Theater> findAllByTheaterNameContainingIgnoreCaseAndIsDeleted(String search , Boolean isDelete, Pageable pageable) ;
    Page<Theater> findAllByIsDeleted(Boolean isDelete , Pageable pageable) ;

    Boolean existsByTheaterName(String name) ;
}
