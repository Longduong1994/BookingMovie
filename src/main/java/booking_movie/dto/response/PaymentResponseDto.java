package booking_movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto implements Serializable {
    private String status;
    private String message;
    private String url;
}
