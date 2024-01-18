package booking_movie.dto.response;

import booking_movie.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private String theaterName;
    private String locationName;
    private String roomName;
    private String movieName;
    private String movieImage;
    private String rated;
    private Set<String> chairs;
    private LocalTime startTime;
    private LocalDateTime bookingDate;
    private String promotion;
    private String coupon;
    private String paymentMethod;
    private Double total;
}
