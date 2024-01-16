package booking_movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenreResponseDto {
    private Long id;
    private String genreName;
    private Boolean isDeleted;
}
