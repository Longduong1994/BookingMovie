package booking_movie.mapper;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.entity.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LocationMapper {
    public LocationResponseDto toResponse(Location location) {
        return LocationResponseDto.builder()
                .id(location.getId())
                .locationName(location.getLocationName())
                .isDelete(location.getIsDelete())
                .build();
    }

    public Location toEntity(LocationRequestDto locationRequestDto) {
        return Location.builder()
                .locationName(locationRequestDto.getLocationName())
                .isDelete(locationRequestDto.getIsDelete())
                .build();
    }
}
