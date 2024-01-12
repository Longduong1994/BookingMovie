package booking_movie.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairResponseDto {
    private Long id ;
    private String chairName ;
    private String chairType ;
    private String roomName  ;
    private Boolean isDeleted ;
}
