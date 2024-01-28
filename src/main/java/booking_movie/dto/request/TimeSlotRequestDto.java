package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    public void setStartTime(String startTime) {
        // Chuyển đổi chuỗi 'HH:mm' thành LocalTime
        this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

}
