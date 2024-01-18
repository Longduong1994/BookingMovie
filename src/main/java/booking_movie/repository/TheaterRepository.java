package booking_movie.repository;

import booking_movie.constants.RoomType;
import booking_movie.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Page<Theater> findAllByTheaterNameContainingIgnoreCaseAndIsDeletedFalse(String search , Pageable pageable) ;
    Page<Theater> findAllByIsDeletedFalse( Pageable pageable) ;
    List<Theater> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    List<Theater> findAllByLocationIsDeleteTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    Boolean existsByTheaterName(String name) ;


    @Query("SELECT t FROM Theater t join t.rooms r join r.timeSlots ts join ts.movie m join t.location l where m.id = :idMovie and ts.movie.stopDate >= :dateBooking and l.id = :idLocation and r.roomType = :type and m.isDeleted = false and l.isDelete = false and t.isDeleted= false and r.isDeleted = false and ts.isDeleted = false ")
    List<Theater> findByMovieAndDateBookingAndLocationAndType(@Param("idMovie") Long idMovie,
                                                              @Param("dateBooking")LocalDate dateBooking,
                                                              @Param("idLocation") Long idLocation,
                                                              @Param("type")RoomType type);

}
