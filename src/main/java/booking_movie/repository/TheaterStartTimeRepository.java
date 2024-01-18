package booking_movie.repository;

import booking_movie.constants.RoomType;
import booking_movie.dto.response.TheaterAndStart;
import booking_movie.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
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

}
