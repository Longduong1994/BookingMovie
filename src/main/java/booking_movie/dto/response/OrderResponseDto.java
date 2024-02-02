package booking_movie.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private String theaterName;
    private String code;
    private String locationName;
    private String roomName;
    private String movieName;
    private String movieImage;
    private String rated;
    private Set<String> chairs;
    private LocalTime startTime;
    private LocalDate bookingDate;
    private String promotion;
    private String coupon;
    private String paymentMethod;
    private Long total;
}
