package booking_movie.dto.response;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlotResponseDto {
    private Long id ;
    private String movieName ;
    private String roomName ;
    private LocalTime startTime ;
}
