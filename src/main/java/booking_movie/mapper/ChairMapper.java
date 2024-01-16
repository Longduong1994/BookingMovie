package booking_movie.mapper;

import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.entity.Chair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ChairMapper {
    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "id",ignore = true)
    Chair toEntity(ChairRequest chairRequest) ;
    @Mapping(target = "roomName", source = "room.roomName")
    ChairResponseDto toResponse(Chair chair) ;
}
