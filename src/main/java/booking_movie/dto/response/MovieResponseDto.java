package booking_movie.dto.response;

import booking_movie.entity.Format;
import booking_movie.constants.MovieStatus;
import booking_movie.entity.Format;
import booking_movie.entity.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieResponseDto {
    private Long id;
    private String movieName;
    private String movieImage;
    private Double price;
    private String director;
    private String cast;
    private String description;
    private String rated;
    private Set<Format> formats;
    private LocalDate stopDate;
    private String language;
    private MovieStatus movieStatus;
    private Long runningTime;
    private LocalDate releaseDate;
    private Set<String> genreName;
}
