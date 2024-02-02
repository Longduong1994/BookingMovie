package booking_movie.dto.request.dish;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class DishRequestDto {

    @NotEmpty(message = "Tên sản phẩm không thể để trống")
    private String dishName;

    @NotNull(message = "Image is required")
    private MultipartFile image;

    @NotNull(message = "Chưa thêm mã danh mục")
    private Long categoryId;

    @Min(value = 0, message = "Giá không thể âm")
    private Double price;

}
