package booking_movie.mapper;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.entity.Location;
import booking_movie.entity.Theater;
import booking_movie.exception.CustomsException;
import booking_movie.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TheaterMapper {
    private LocationRepository locationRepository ;
    public TheaterResponseDto toResponse(Theater theater) {
        return TheaterResponseDto.builder()
                .id(theater.getId())
                .theaterName(theater.getTheaterName())
                .isDeleted(theater.getIsDeleted())
                .locationName(theater.getLocation().getLocationName())
                .build();
    }

    public Theater toEntity(TheaterRequestDto theaterRequestDto) throws CustomsException {
        Location location = locationRepository.findById(theaterRequestDto.getLocationId()).orElseThrow(()-> new CustomsException("Vị trí không tồn tại"));
        return Theater.builder()
                .theaterName(theaterRequestDto.getTheaterName())
                .isDeleted(theaterRequestDto.getIsDeleted())
                .location(location)
                .build();
    }
}
