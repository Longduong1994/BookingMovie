package booking_movie.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "Not null")
    private Long theaterId;

    private Long paymentId;

    @NotNull(message = "Not null")
    private Long promotionId;
}
