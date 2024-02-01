package booking_movie.dto.request.dish;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class DishRequestDto {

    @NotNull(message = "dishName not found")
    private String dishName;

    @NotNull(message = "Image is required")
    private MultipartFile image;

    @Positive(message = "CategoryId is incorrect syntax")
    private Long categoryId;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

}
