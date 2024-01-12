package booking_movie.dto.response;
import booking_movie.entity.Format;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieResponseDto {
    private Long id;
    private String movieName;
    private String movieImage;
    private String director;
    private String cast;
    private Long runningTime;
    private LocalDate releaseDate;
    private String genres;
}
