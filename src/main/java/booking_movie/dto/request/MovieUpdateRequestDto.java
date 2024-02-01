package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieUpdateRequestDto {
    @NotEmpty(message = "Tên phim không thể để trống")
    private String movieName;
    private MultipartFile movieImage;
    @NotNull(message = "Giá không thể để trống")
    private Double price;
    @NotEmpty(message = "Tên tác giả không thể để trống")
    private String director;
    @NotEmpty(message = "Tên diễn viên không thể để trống")
    private String cast;
    @NotEmpty(message = "Mô tả phim không thể để trống")
    private String description;
    @NotNull(message = "Thời lượng không thể để trống")
    @Min(value = 45, message = "Thời lượng ít nhất là 45 phút")
    @Max(value = 240, message = "Thời lượng không thể nhiều hn 240 phút")
    private Long runningTime;
    @NotEmpty(message = "Ngôn ngữ không thể để trống")
    private String language;
    private Set<Long> genreId;
}
