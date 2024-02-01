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
public class ChairRequest {
    @NotEmpty(message = "Tên ghế không thể trống")
    private String chairName ;
    @NotEmpty(message = "Kiểu ghế không thể để trống")
    @NotNull(message = "Chưa thêm kiểu ghế")
    private String chairType ;
    @NotNull(message = "Chưa thêm mã phòng chiếu")
    private Long roomId;
    private Boolean isDeleted ;
}
