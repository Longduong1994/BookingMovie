package booking_movie.dto.request.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DishRequestDto {
    private String dishName;
    private Long categoryId;
}
