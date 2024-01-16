package booking_movie.mapper;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.entity.Theater;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TheaterMapper {
    @Mapping(target ="location.id" , source = "locationId")
    @Mapping(target = "id",ignore = true)
    Theater toEntity(TheaterRequestDto theaterRequestDto) ;

    @Mapping(target = "locationName", source = "location.locationName")
    TheaterResponseDto toResponse(Theater theater);
}
