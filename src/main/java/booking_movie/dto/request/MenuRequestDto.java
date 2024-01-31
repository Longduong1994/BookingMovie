package booking_movie.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MenuRequestDto {
    @NotNull(message = "Mã Menu không thể để trống")
    private Long id;
    @NotNull(message = "Số lượng không thể để trống")
    private Integer quantity;
    @NotNull(message = "Mã sản phẩm không thể để trống")
    private Long dishId;
    @NotNull(message = "Mã giỏ hàng không thể để trống")
    private Long orderId;
}
