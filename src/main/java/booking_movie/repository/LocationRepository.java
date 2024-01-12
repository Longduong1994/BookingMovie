package booking_movie.repository;

import booking_movie.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long> {
    Page<Location> findAllByLocationNameContainingIgnoreCaseAndIsDelete(String search, Boolean isDelete, Pageable pageable) ;
    Page<Location> findAllByIsDelete(Boolean isDelete, Pageable pageable) ;
    Boolean existsByLocationName (String locationName) ;
}
