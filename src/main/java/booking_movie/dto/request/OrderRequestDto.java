package booking_movie.dto.request;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "Không được để trống.")
    private String theater;
    @NotNull(message = "Không được để trống.")
    private String location;
    @NotNull(message = "Không được để trống.")
    private Long movieId;
    @NotNull(message = "Không được để trống.")
    private Long roomId;
    @NotNull(message = "Không được để trống.")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @NotNull(message = "Không được để trống.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    @NotNull(message = "Không được để trống.")
    private Set<Long> chairIds;
    private Long total;
    private Long point;
    private String promotion;
    private String coupon;
    private Long paymentId;
}
