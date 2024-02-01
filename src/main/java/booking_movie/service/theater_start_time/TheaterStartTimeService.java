package booking_movie.service.theater_start_time;

import booking_movie.dto.request.BookingMovieRequest;
import booking_movie.dto.response.ChairByRoomDto;
import booking_movie.entity.Location;
import booking_movie.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface TheaterStartTimeService {
    Map<String, List<LocalTime>> findTheaterAndStartTime(Long idMovie, LocalDate selectDate,String roomType,String locationName);

    List<Location> findLocationMovie(Long idMovie, LocalDate selectDate,String roomType);

    List<Room> getRoomByMovieId(Long idMovie, LocalDate selectDate, String roomType, LocalTime startTime, String theaterName);

    List<ChairByRoomDto> getChairByRoom(Long idRoom,LocalTime startTime);

}
