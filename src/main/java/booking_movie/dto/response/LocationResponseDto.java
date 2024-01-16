package booking_movie.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationResponseDto {
    private Long id ;
    private String locationName ;
    public LocalDateTime createDateTime;
    public LocalDateTime updateDateTime;
    public Integer createUser;
    public Integer updateUser;
    private Boolean isDelete = false ;
}
