package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class LocationRequestDto {
    @NotBlank(message = "Location is not blank")
    @NotEmpty(message = "Location is not empty")
    private String locationName ;
    private Boolean isDelete = false ;
}
