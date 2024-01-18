package booking_movie.dto.request.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DateTimeAndLocationAndTypeRequest {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate DateBooking ;
    private Long idLocation ;
    private String type ;
}
