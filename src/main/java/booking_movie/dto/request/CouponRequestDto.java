package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author huyqt97
 */
@AllArgsConstructor
@Data
@Builder
public class CouponRequestDto {
    @NotNull(message = "Chưa thêm ngày hiệu lực")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate effectDate;

    @NotNull(message = "Chưa thêm giảm giá")
    private Double salePrice;

    @NotNull(message = "Not null")
    private String description;

    private List<UserId> listUserId;
}