package booking_movie.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreRequestDto {
    @NotEmpty(message = "genreName is not empty")
    private String genreName;
    private Boolean isDeleted =false;
}
