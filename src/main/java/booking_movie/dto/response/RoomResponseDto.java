package booking_movie.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomResponseDto {
    private Long id ;
    private String roomName ;
    private Integer numberOfSeatsInARow ;
    private Integer numberOfSeatsInAColumn ;
    private String roomType ;
    private String theaterName ;
    private Boolean isDeleted ;
}
