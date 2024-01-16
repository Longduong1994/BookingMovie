package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairRequest {
    @NotEmpty(message = "chairName is not empty")
    @NotBlank(message = "chairName is not blank")
    private String chairName ;
    @NotEmpty(message = "chairType is not empty")
    @NotBlank(message = "chairType is not blank")
    private String chairType ;
    @NotNull(message = "roomId is not null")
    private Long roomId;
    private Boolean isDeleted ;
}
