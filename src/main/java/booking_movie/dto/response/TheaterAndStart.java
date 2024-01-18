package booking_movie.dto.response;

import java.time.LocalTime;

public interface TheaterAndStart {
    String getTheaterName();
    LocalTime getStartTime();
}