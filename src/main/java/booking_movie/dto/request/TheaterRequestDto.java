package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterRequestDto {
    @NotEmpty(message = "theaterName is not empty")
    @NotBlank(message = "theaterName is not blank")
    private String theaterName ;
    @NotNull(message = "locationId is not null")
    private Long locationId ;
    private Boolean isDeleted;
}
