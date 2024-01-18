package booking_movie.dto.request;


import booking_movie.constants.Status;
import booking_movie.entity.Payment;
import booking_movie.entity.Promotion;
import booking_movie.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime bookingDate;
    private Set<Long> chairIds;
    private String promotion;
    private String coupon;
    private Long paymentId;
}
