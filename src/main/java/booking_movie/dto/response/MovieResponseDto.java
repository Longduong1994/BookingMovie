package booking_movie.dto.response;
import booking_movie.constants.MovieStatus;
import booking_movie.entity.Format;
import booking_movie.entity.Genre;
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
    private String description;
    private Boolean isDeleted;
    private Set<Format> formats;
    private LocalDate stopDate;
    private MovieStatus movieStatus;
    private Long runningTime;
    private LocalDate releaseDate;
    private Set<Genre> genres;
}
