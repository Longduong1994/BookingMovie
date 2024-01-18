package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlotRequestDto {
    @NotNull(message = "movieId is not null")
    private Long movieId ;
    @NotNull(message = "roomId is not null")
    private Long roomId ;
    private Long theaterId ;
    @NotNull(message = "Time is not null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime ;
    private Boolean isDeleted;
}
