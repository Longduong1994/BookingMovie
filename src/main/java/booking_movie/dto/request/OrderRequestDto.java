package booking_movie.dto.request;



import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long theaterId;
    private Long locationId;
    private Long movieId;
    private Long roomId;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    private Set<Long> chairIds;
    private String promotion;
    private String coupon;
    private Long paymentId;
}
