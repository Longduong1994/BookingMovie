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
    @NotEmpty(message = "Tên phim không thể để trống")
    private String movieName;
    @NotNull(message = "Hình ảnh phim không thể để trống")
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày khởi chiếu không thể để trống")
    private LocalDate releaseDate;

//    private Set<Long> formats;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày kết thúc không thể để trống")
    private LocalDate stopDate;

    @NotEmpty(message = "Ngôn ngữ không thể để trống")
    private String language;

    @NotEmpty(message = "Thể loại không thể để trống")
//    private String rated;

    private Set<Long> genreId;
}
