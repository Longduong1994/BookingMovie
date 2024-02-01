package booking_movie.repository;

import booking_movie.constants.RoomType;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByRoomNameContainingIgnoreCaseAndIsDeletedFalse(String search, Pageable pageable) ;
    Page<Room> findAllByIsDeletedFalse( Pageable pageable) ;
    List<Room> findAllByTheaterIdAndIsDeletedFalse(Long idTheater);
    List<Room> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime);
    List<Room> findAllByTheaterIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime);

    Boolean existsByRoomNameAndTheaterId(String name , Long id) ;

    @Query("SELECT  r FROM Room r JOIN r.timeSlots ts join ts.movie m join r.theater t join t.location l where m.id = :idMovie and m.stopDate >= :dateBooking and l.id = :idLocation and r.roomType = :type and ts.id = :idTimeSlot and l.isDelete = false and t.isDeleted = false and r.isDeleted = false and ts.isDeleted = false and m.isDeleted = false ")
    Room findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(@Param("idMovie") Long idMovie,
                                                                                  @Param("dateBooking") LocalDate dateBooking,
                                                                                  @Param("idLocation") Long idLocation,
                                                                                  @Param("type")RoomType type ,
                                                                                  @Param("idTimeSlot") Long idTimeSlot);
}
