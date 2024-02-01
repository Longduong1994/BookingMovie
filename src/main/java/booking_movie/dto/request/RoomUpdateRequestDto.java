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
@Builder
public class RoomUpdateRequestDto {
    @NotEmpty(message = "Tên phòng chiếu không thể để trống")
    @NotBlank(message = "Tên phòng chiếu không thể để trống")
    private String roomName ;
    @NotEmpty(message = "Kiểu phòng chiếu không thể để trống")
    @NotBlank(message = "Kiểu phòng chiếu không thể để trống")
    private String roomType ;
    @NotNull(message = "Mã rạp chiếu không thể trống")
    private Long theaterId;
    private Boolean isDelete ;
}
