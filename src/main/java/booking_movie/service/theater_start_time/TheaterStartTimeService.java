package booking_movie.service.theater_start_time;

import booking_movie.dto.request.BookingMovieRequest;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface TheaterStartTimeService {
    Map<String, List<LocalTime>> findTheaterAndStartTime(BookingMovieRequest bookingMovieRequest, Long idMovie);
}
