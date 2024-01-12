package booking_movie.mapper;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LocationMapper {
    Location toEntity(LocationRequestDto locationRequestDto) ;
    LocationResponseDto toResponse (Location location) ;
}
