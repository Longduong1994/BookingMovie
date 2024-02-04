package booking_movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
public class CouponResponseDto {

    /**
     * form request Coupon
     *
     * @author huyqt97
     */

    private Long id;
    private String code;
    private String description;
    private LocalDate endDate;
    private Double sale;
    private String user;
    private Boolean status;
    private Boolean isDelete;
}