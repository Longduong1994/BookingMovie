package booking_movie.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private LocalTime startTime ;
    private Boolean isDeleted;
}
