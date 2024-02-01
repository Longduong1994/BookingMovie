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
    @NotEmpty(message = "Tên vị trí không thể trống")
    private String locationName ;
    private Boolean isDelete = false ;
}
