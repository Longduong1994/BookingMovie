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
    @NotEmpty(message = "Tên rạp chiếu không thể để trống")
    private String theaterName ;
    @NotNull(message = "Mã vị trí không thể để trống")
    private Long locationId ;
    private Boolean isDeleted;
}
