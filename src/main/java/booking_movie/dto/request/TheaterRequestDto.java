package booking_movie.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterRequestDto {
    private String theaterName ;
    private Long locationId ;
    private Boolean isDeleted;
}
