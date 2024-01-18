package booking_movie.repository;

import booking_movie.constants.RoomType;
import booking_movie.entity.Chair;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ChairRepository extends JpaRepository<Chair, Long> {

    List<Chair> findAllByIsDeletedFalse() ;

    List<Chair> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    List<Chair> findAllByRoomIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;

    Boolean existsByChairNameAndRoomId(String name , Long idRoom) ;

    @Query("SELECT c FROM Chair c join c.room r join r.timeSlots ts join ts.movie m join r.theater t join t.location l where m.id = :idMovie and m.stopDate >= :dateBooking and l.id = :idLocation and r.roomType = :type and ts.id = :idTimeSlot and m.isDeleted = false and l.isDelete = false and t.isDeleted = false and r.isDeleted = false and ts.isDeleted = false and c.isDeleted= false ")

    List<Chair> findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(@Param("idMovie") Long idMovie,
                                                                       @Param("dateBooking")LocalDate dateBooking,
                                                                       @Param("idLocation") Long idLocation,
                                                                       @Param("type") RoomType type,
                                                                       @Param("idTimeSlot") Long idTimeSlot);
}
