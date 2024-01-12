package booking_movie.service.chair;

import booking_movie.constants.ChairType;
import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.request.ChairTypeRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.entity.Chair;
import booking_movie.entity.Room;
import booking_movie.exception.ChairException;
import booking_movie.exception.RoomException;
import booking_movie.mapper.ChairMapper;
import booking_movie.repository.ChairRepository;
import booking_movie.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChairServiceImpl implements ChairService{
    private ChairRepository chairRepository ;
    private RoomRepository roomRepository ;
    private ChairMapper chairMapper ;

    @Override
    public List<ChairResponseDto> findAll() {
        List<Chair> list = chairRepository.findAllByIsDeleted(false) ;
        return list.stream().map(item -> chairMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public ChairResponseDto findById(Long id) throws ChairException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new ChairException("Chair Not Found")) ;
        return chairMapper.toResponse(chair);
    }

    @Override
    public ChairResponseDto changeChairType(Long id, ChairTypeRequest chairTypeRequest) throws ChairException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new ChairException("Chair Not Found")) ;
        switch (chairTypeRequest.getChairType()) {
            case "normal":
                chair.setChairType(ChairType.NORMAL);
                break;
            case "VIP":
                chair.setChairType(ChairType.VIP);
                break;
            default:
                throw new ChairException("ChairType Not Found");
        }
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }


    @Override
    public ChairResponseDto update(Long id, ChairRequest chairRequest) throws ChairException, RoomException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new ChairException("Chair Not Found")) ;
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new RoomException("Room Not Found")) ;
        chair.setId(id);
        chair.setChairName(chairRequest.getChairName());
        switch (chairRequest.getChairType()) {
            case "normal":
                chair.setChairType(ChairType.NORMAL);
                break;
            case "VIP":
                chair.setChairType(ChairType.VIP);
                break;
            default:
                throw new ChairException("ChairType Not Found");
        }
        chair.setRoom(room);
        chair.setIsDeleted(chairRequest.getIsDeleted());
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }

    @Override
    public void delete(Long id) throws ChairException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new ChairException("Chair Not Found")) ;
        chair.setIsDeleted(!chair.getIsDeleted());
        chairRepository.save(chair);
    }

}
