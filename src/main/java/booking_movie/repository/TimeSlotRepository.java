package booking_movie.repository;

import booking_movie.constants.RoomType;
import booking_movie.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot , Long> {
    List<TimeSlot> findAllByIsDeletedFalse();
    List<TimeSlot> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime);
    List<TimeSlot> findAllByMovieIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    List<TimeSlot> findAllByRoomIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;

    Boolean existsByRoomTheaterIdAndRoomIdAndMovieIdAndStartTime(Long idTheater, Long idRoom, Long idMovie, LocalTime localTime) ;

    @Query("SELECT ts FROM TimeSlot ts join ts.movie m JOIN ts.room r join ts.room.theater t join ts.room.theater.location l where ts.movie.id = :idMovie and ts.movie.stopDate >= :dateBooking and ts.room.theater.location.id = :idLocation and ts.room.roomType = :type  and m.isDeleted = false and ts.isDeleted = false and r.isDeleted = false and t.isDeleted = false and l.isDelete = false ")
    List<TimeSlot> findAllByIdMovieAndDateBookingAndIdLocationAndTypeAndIdTheater(@Param("idMovie")Long idMovie ,
                                                                                            @Param("dateBooking") LocalDate dateBooking,
                                                                                            @Param("idLocation") Long idLocation,
                                                                                            @Param("type")RoomType type );

}
