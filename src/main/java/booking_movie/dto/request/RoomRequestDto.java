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
public class RoomRequestDto {
        @NotEmpty(message = "roomName is not empty")
        @NotBlank(message = "roomName is not blank")
        private String roomName ;
        @NotNull(message = "numberOfSeatsInARow is not null")
        private Integer numberOfSeatsInARow ;
        @NotNull(message = "numberOfSeatsInAColumn is not null")
        private Integer numberOfSeatsInAColumn ;
        @NotEmpty(message = "roomType is not empty")
        @NotBlank(message = "roomType is not blank")
        private String roomType ;
        @NotNull(message = "theaterId is not null")
        private Long theaterId ;
        private Boolean isDeleted ;
}
