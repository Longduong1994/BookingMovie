package booking_movie.dto.request;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotEmpty(message = "Mã rạp chiếu không thể trống")
    @NotBlank(message = "Mã rạp chiếu không thể trống")
    private Long theaterId;
    @NotBlank(message = "Mã vị trí không thể để trống")
    @NotEmpty(message = "Mã vị trí không thể để trống")
    private Long locationId;
    @NotEmpty(message = "Mã phim không thể trống")
    @NotBlank(message = "Mã phim không thể trống")
    private Long movieId;
    @NotEmpty(message = "Mã phòng chiếu không thể trống")
    @NotBlank(message = "Mã phòng chiếu không thể trống")
    private Long roomId;
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "Thời gian không thể để trống")
    private LocalTime startTime;
    @NotNull(message = "Ngày không thể để trống")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    private Set<Long> chairIds;
    private String promotion;
    private String coupon;
    private Long paymentId;
}
