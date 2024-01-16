package booking_movie.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomRequestDto {
    private String roomName ;
    private Integer numberOfSeatsInARow ;
    private Integer numberOfSeatsInAColumn ;
    private String roomType ;
    private Long theaterId ;
    private Boolean isDeleted ;
}
