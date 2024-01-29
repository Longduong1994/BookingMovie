package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author huyqt97
 */
@AllArgsConstructor
@Data
@Builder
public class CouponRequestDto {
    @NotNull(message = "Chưa thêm ngày hiệu lực")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate effectDate;
    @NotNull(message = "Chưa thêm giảm giá")
    private Double salePrice;
    private String description;
    @NotNull(message = "Thêm mã khách hàng")
    private Long user;
}