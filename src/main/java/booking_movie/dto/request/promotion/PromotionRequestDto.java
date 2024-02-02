package booking_movie.dto.request.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PromotionRequestDto {
    @NotNull(message="Chưa thêm tên sự kiện")
    private String eventName;
    private MultipartFile image;
    private String description;
    private Double salePrice;
    @NotNull(message = "Chưa thêm ngày bắt đầu")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @NotNull(message = "Chưa thêm ngày hết hạn")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
}
