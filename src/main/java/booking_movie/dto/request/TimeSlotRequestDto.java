package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlotRequestDto {
    @NotNull(message = "Mã phim không thể trống")
    private Long movieId ;
    @NotNull(message = "Mã phòng chiếu không thể trống")
    private Long roomId ;
    @NotNull(message = "Mã rạp chiếu không thể trống")
    private Long theaterId ;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày khởi chiếu không thể trống")
    private LocalDate showDateMovie;
    @NotNull(message = "Thời gian bắt đầu không thể để trống")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime ;
    private Boolean isDeleted;
    public void setStartTime(String startTime) {
        // Chuyển đổi chuỗi 'HH:mm' thành LocalTime
        this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
    }

}
