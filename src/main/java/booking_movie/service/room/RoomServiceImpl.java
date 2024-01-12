package booking_movie.service.room;

import booking_movie.constants.RoomType;
import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.entity.Room;
import booking_movie.entity.Theater;
import booking_movie.exception.RoomException;
import booking_movie.exception.TheaterException;
import booking_movie.mapper.RoomMapper;
import booking_movie.repository.RoomRepository;
import booking_movie.repository.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository ;
    private RoomMapper roomMapper ;
    private TheaterRepository theaterRepository ;

    @Override
    public Page<RoomResponseDto> findAll(String search, Pageable pageable) {
        Page<Room> roomPage ;
        if (search.isEmpty()) {
            roomPage = roomRepository.findAllByIsDeleted(false , pageable) ;
        } else  {
            roomPage = roomRepository.findAllByRoomNameContainingIgnoreCaseAndIsDeleted(search,false, pageable);
        }
        return roomPage.map(item -> roomMapper.toResponse(item));
    }

    @Override
    public RoomResponseDto findById(Long id) throws RoomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomException("Room Not Found")) ;
        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponseDto save(RoomRequestDto roomRequestDto) throws RoomException, TheaterException {
        if (roomRepository.existsByRoomName(roomRequestDto.getRoomName())){
            throw new RoomException("Exits RoomName") ;
        }
        if (roomRequestDto.getNumberOfSeatsInARow() < 0 && roomRequestDto.getNumberOfSeatsInAColumn() < 0 ) {
            throw new RoomException("The number of seats cannot be negative") ;
        }
        Theater theater = theaterRepository.findById(roomRequestDto.getTheaterId()).orElseThrow(() -> new TheaterException("Theater Not Found"));
        RoomType roomType ;
        switch (roomRequestDto.getRoomType()) {
            case "2D":
                roomType = RoomType.TWO_D;
                break;
            case "3D":
                roomType = RoomType.THREE_D;
                break;
            case "4D":
                roomType= RoomType.FOUR_D;
                break;
            default:
                throw new RoomException("RoomType Not Found") ;
        }
        Room room = new Room();
        room.setRoomName(roomRequestDto.getRoomName());
        room.setNumberOfSeatsInARow(roomRequestDto.getNumberOfSeatsInARow());
        room.setNumberOfSeatsInAColumn(roomRequestDto.getNumberOfSeatsInAColumn());
        room.setRoomType(roomType);
        room.setTheater(theater);
        room.setIsDeleted(roomRequestDto.getIsDeleted());
        roomRepository.save(room);

        // TODO : create Chair
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Vòng lặp để in ra hình chữ nhật


        return null;
    }

    @Override
    public RoomResponseDto update(Long id, RoomRequestDto roomRequestDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
