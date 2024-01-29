package booking_movie.dto.request;

import booking_movie.entity.Format;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieRequestDto {
    @NotEmpty(message = "movieName is not empty")
    private String movieName;
    private MultipartFile movieImage;
    private Double price;
    @NotEmpty(message = "director is not empty")
    private String director;

    @NotEmpty(message = "cast is not empty")
    private String cast;

    @NotEmpty(message = "description is not empty")
    private String description;

    @NotNull(message = "runningTime is not null")
    @Min(value = 45, message = "runningTime must be at least 45 minutes")
    @Max(value = 240, message = "runningTime must be at most 240 minutes")
    private Long runningTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

//    private Set<Long> formats;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate stopDate;

    @NotEmpty(message = "language is not empty")
    private String language;

    @NotEmpty(message = "rated is not empty")
//    private String rated;

    private Set<Long> genreId;
}
