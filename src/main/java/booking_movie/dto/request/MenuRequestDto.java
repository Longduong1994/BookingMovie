package booking_movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MenuRequestDto {
    private Long id;
    private Integer quantity;
    private Long dishId;
    private Long orderId;
}
