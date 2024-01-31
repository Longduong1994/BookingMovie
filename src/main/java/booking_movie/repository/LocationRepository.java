package booking_movie.repository;

import booking_movie.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long> {
    Page<Location> findAllByLocationNameContainingIgnoreCaseAndIsDeleteFalse(String search,  Pageable pageable);
    List<Location> findAllByIsDeleteTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    Page<Location> findAllByIsDeleteFalse( Pageable pageable) ;
    Boolean existsByLocationName (String locationName) ;

}
