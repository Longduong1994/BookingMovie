package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairTypeRequest {
    @NotEmpty(message = "Kiểu ghế không thể để trống")
    @NotBlank(message = "Kiểu ghế không chứa khoảng trắng")
    private String chairType ;
}
