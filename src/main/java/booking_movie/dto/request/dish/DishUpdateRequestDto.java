package booking_movie.dto.request.dish;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class DishUpdateRequestDto {
    private Long id;
    @NotEmpty
    private String dishName;

    @Positive(message = "CategoryId is incorrect syntax")
    private Long categoryId;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;
}
