package booking_movie.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huyqt97
 */
@AllArgsConstructor
@Data
public class CategoryUpdateRequestDto {
    private Long id;
    private String categoryName;
    private Boolean isDeleted;
}
