package booking_movie.dto.request;

import booking_movie.constants.RoomType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class BookingMovieRequest {
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate selectDate;
    String roomType;
    String locationName;
}
