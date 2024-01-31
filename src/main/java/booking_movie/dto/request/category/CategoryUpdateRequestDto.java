package booking_movie.dto.request.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author huyqt97
 */
@AllArgsConstructor
@Data
public class CategoryUpdateRequestDto {
    @Positive(message = "CategoryId is incorrect syntax")
    private Long id;
    @NotEmpty(message = "Tên danh mục không thể để trống")
    private String categoryName;
}
