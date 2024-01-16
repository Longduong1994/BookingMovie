package booking_movie.dto.request.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author huyqt97
 */
@AllArgsConstructor
@Data
public class CategoryRequestDto {
    @NotEmpty
    private String categoryName;
}
