package booking_movie.dto.request;

import booking_movie.constants.RoomType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class BookingMovieRequest {
    @NotNull(message = "Chưa chọn ngày chiếu")
    @JsonFormat(pattern = "yyyy/MM/dd")
    LocalDate selectDate;
    @NotNull(message = "Chưa chọn kiểu phòng chiếu")
    String roomType;
    @NotNull(message = "Chưa chọn tên vị trí")
    String locationName;
}
