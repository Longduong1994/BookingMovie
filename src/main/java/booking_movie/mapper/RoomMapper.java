package booking_movie.mapper;

import booking_movie.constants.RoomType;
import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.entity.Room;
import booking_movie.entity.Theater;
import booking_movie.exception.CustomsException;
import booking_movie.repository.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomMapper {

    private TheaterRepository theaterRepository ;
    public RoomResponseDto toResponse(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .numberOfSeatsInAColumn(room.getNumberOfSeatsInAColumn())
                .numberOfSeatsInARow(room.getNumberOfSeatsInARow())
                .roomType(room.getRoomType().name())
                .isDeleted(room.getIsDeleted())
                .theaterName(room.getTheater().getTheaterName())
                .build();
    }

    public Room toEntity(RoomRequestDto roomRequestDto) throws CustomsException {
        Theater theater  = theaterRepository.findById(roomRequestDto.getTheaterId()).orElseThrow(() -> new CustomsException("Theater Not Found"));
        RoomType roomType = switch (roomRequestDto.getRoomType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException("RoomType Not Found");
        };
        return Room.builder()
                .roomName(roomRequestDto.getRoomName())
                .numberOfSeatsInARow(roomRequestDto.getNumberOfSeatsInARow())
                .numberOfSeatsInAColumn(roomRequestDto.getNumberOfSeatsInAColumn())
                .roomType(roomType)
                .isDeleted(roomRequestDto.getIsDeleted())
                .theater(theater)
                .build();
    }
}
