package booking_movie.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterResponseDto {
    private Long id ;
    private String theaterName ;
    private Boolean isDeleted;
    private String locationName ;
}
