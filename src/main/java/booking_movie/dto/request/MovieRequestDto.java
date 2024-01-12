package booking_movie.dto.request;

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
public class MovieRequestDto {
    private String movieName;
    private String movieImage;
    private String director;
    private String cast;
    private Long runningTime;
    private LocalDate releaseDate;
    private LocalDate stopDate;
    private String language;
    private String rated;
    private Set<Genre> genres;
}
