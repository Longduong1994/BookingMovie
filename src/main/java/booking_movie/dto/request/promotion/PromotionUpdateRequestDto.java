package booking_movie.dto.request.promotion;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PromotionUpdateRequestDto {
    private Long id;
    @NotEmpty
    private String eventName;

    private String eventCode;

    private Double salePrice;

    private Integer salePercent;

    private LocalDate startDate;

    private LocalDate endDate;
}
