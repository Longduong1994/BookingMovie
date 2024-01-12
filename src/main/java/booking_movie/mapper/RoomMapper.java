package booking_movie.mapper;

import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoomMapper {
    @Mapping(target = "theaterName", source = "theater.theaterName")
    RoomResponseDto toResponse(Room room) ;
}
