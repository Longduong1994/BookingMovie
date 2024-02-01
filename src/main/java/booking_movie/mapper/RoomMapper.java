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
    public RoomResponseDto toResponse(Room room) throws CustomsException {
        String typeRoom = switch (room.getRoomType()) {
            case TWO_D -> "2D" ;
            case THREE_D -> "3D";
            case FOUR_D -> "4D";
            default -> throw new CustomsException("Kiểu Phòng Không tồn tại") ;
        };
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .numberOfSeatsInAColumn(room.getNumberOfSeatsInAColumn())
                .numberOfSeatsInARow(room.getNumberOfSeatsInARow())
                .roomType(typeRoom)
                .isDeleted(room.getIsDeleted())
                .theaterName(room.getTheater().getTheaterName())
                .build();
    }

    public Room toEntity(RoomRequestDto roomRequestDto) throws CustomsException {
        Theater theater  = theaterRepository.findById(roomRequestDto.getTheaterId()).orElseThrow(() -> new CustomsException("Rạp chiếu không tồn tại"));
        RoomType roomType = switch (roomRequestDto.getRoomType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException("Kiểu phòng chiếu không tồn tại");
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
