package booking_movie.repository;

import booking_movie.constants.RoomType;
import booking_movie.dto.response.ChairByRoomDto;
import booking_movie.dto.response.TheaterAndStart;
import booking_movie.entity.Location;
import booking_movie.entity.Room;
import booking_movie.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface TheaterStartTimeRepository extends JpaRepository<Ticket, Long> {


    @Query(value = "SELECT theater.theaterName AS theaterName, timeslot.startTime AS startTime " +
            "FROM Movie movie " +
            "JOIN TimeSlot timeslot ON movie.id = timeslot.movie.id " +
            "JOIN Room room ON timeslot.room.id = room.id " +
            "JOIN Theater theater ON room.theater.id = theater.id " +
            "JOIN Location location ON theater.location.id = location.id " +
            "WHERE room.roomType = :roomType " +
            "AND location.locationName = :locationName " +
            "AND movie.id = :movieId " +
            "AND timeslot.showDateMovie BETWEEN movie.releaseDate AND movie.stopDate " +
            "AND timeslot.showDateMovie = :selectedDate")
    List<TheaterAndStart> findTheaterAndStartTime(@Param("roomType") RoomType roomType,
                                                  @Param("locationName") String locationName,
                                                  @Param("movieId") Long movieId,
                                                  @Param("selectedDate") LocalDate selectedDate);

    @Query("SELECT DISTINCT location " +
            "FROM Movie movie " +
            "JOIN TimeSlot timeslot ON movie.id = timeslot.movie.id " +
            "JOIN Room room ON timeslot.room.id = room.id " +
            "JOIN Theater theater ON room.theater.id = theater.id " +
            "JOIN Location location ON theater.location.id = location.id " +
            "WHERE room.roomType = :roomType " +
            "AND movie.id = :movieId " +
            "AND timeslot.showDateMovie BETWEEN movie.releaseDate AND movie.stopDate " +
            "AND timeslot.showDateMovie = :selectedDate")
    List<Location> findDistinctLocationsByRoomTypeAndMovieIdAndShowDate(
            RoomType roomType, Long movieId, LocalDate selectedDate);

    @Query("SELECT r FROM Room r " +
            "JOIN r.theater t " +
            "JOIN r.timeSlots ts " +
            "JOIN ts.movie m " +
            "WHERE t.theaterName = :theaterName " +
            "AND ts.showDateMovie = :showDateMovie " +
            "AND r.roomType = :roomType " +
            "AND ts.startTime = :startTime " +
            "AND m.id = :movieId")
    List<Room> findRoomsByTheaterNameAndShowDateAndStartTimeAndMovieId(
            @Param("theaterName") String theaterName,
            @Param("showDateMovie") LocalDate showDateMovie,
            @Param("startTime") LocalTime startTime,
            @Param("movieId") Long movieId,
            @Param("roomType") RoomType roomType);



    @Query(value = "SELECT c.id, c.chair_name, c.chair_type, " +
            "CASE " +
            "WHEN tk.chair_id IS NOT NULL AND bk.start_time = :startTime THEN 'Đã đặt' " +
            "ELSE 'Trống' " +
            "END " +
            "FROM chair c " +
            "JOIN room r ON c.room_id = r.id " +
            "LEFT JOIN ticket_chair tk ON tk.chair_id = c.id " +
            "LEFT JOIN booking_movie bk ON bk.id = tk.ticket_id " +
            "WHERE r.id = :roomId AND (bk.start_time = :startTime OR bk.start_time IS NULL)",
            nativeQuery = true)
    List<Object[]> getChairsData(@Param("startTime") LocalTime startTime, @Param("roomId") Long roomId);


}
