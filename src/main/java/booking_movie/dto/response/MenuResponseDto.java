package booking_movie.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuResponseDto {
    private Long id;
    private Integer quantity;
    private String dishName;
    private Double price;
}
