package booking_movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
    /**
     * @author huyqt97
     */
@AllArgsConstructor
@Data
public class CouponRequestDto {
    private String description;
    private Long user;
}
