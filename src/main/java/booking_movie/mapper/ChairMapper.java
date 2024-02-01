package booking_movie.mapper;

import booking_movie.constants.ChairType;
import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.entity.Chair;
import booking_movie.entity.Room;
import booking_movie.exception.CustomsException;
import booking_movie.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ChairMapper {
    private RoomRepository roomRepository ;
    public ChairResponseDto toResponse(Chair chair) {
        return ChairResponseDto.builder()
                .id(chair.getId())
                .chairName(chair.getChairName())
                .chairType(chair.getChairType().name())
                .roomName(chair.getRoom().getRoomName())
                .isDeleted(chair.getIsDeleted())
                .build();
    }

    public Chair toEntity(ChairRequest chairRequest) throws CustomsException {
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomsException("Phòng chiếu không tồn tại"));
        ChairType chairType = switch (chairRequest.getChairType()) {
            case "normal" -> ChairType.NORMAL;
            case "VIP" -> ChairType.VIP;
            default -> throw new CustomsException(chairRequest.getChairType() + " không tồn tại");
        };
        return Chair.builder()
                .chairName(chairRequest.getChairName())
                .chairType(chairType)
                .isDeleted(chairRequest.getIsDeleted())
                .room(room)
                .build();
    }

}
