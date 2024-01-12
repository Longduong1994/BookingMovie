package booking_movie.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairRequest {
    private String chairName ;
    private String chairType ;
    private Long roomId;
    private Boolean isDeleted ;
}
