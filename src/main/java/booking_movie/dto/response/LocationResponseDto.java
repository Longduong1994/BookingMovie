package booking_movie.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class LocationResponseDto {
    private Long id ;
    private String locationName ;
    private Boolean isDelete = false ;
}
