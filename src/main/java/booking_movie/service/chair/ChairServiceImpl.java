package booking_movie.service.chair;

import booking_movie.constants.ChairType;
import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.request.ChairTypeRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.entity.Chair;
import booking_movie.entity.Room;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.ChairMapper;
import booking_movie.repository.ChairRepository;
import booking_movie.repository.RoomRepository;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<Chair> list = chairRepository.findAllByIsDeletedFalse() ;
        return list.stream().map(item -> chairMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public ChairResponseDto findById(Long id) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        return chairMapper.toResponse(chair);
    }

    @Override
    public ChairResponseDto changeChairType(Authentication authentication, Long id, ChairTypeRequest chairTypeRequest) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        switch (chairTypeRequest.getChairType()) {
            case "normal":
                chair.setChairType(ChairType.NORMAL);
                break;
            case "VIP":
                chair.setChairType(ChairType.VIP);
                break;
            default:
                throw new CustomsException("ChairType Not Found");
        }
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }


    @Override
    public ChairResponseDto update(Authentication authentication,Long id, ChairRequest chairRequest) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomsException("Room Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
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
                throw new CustomsException(chairRequest.getChairType() + " Not Found");
        }
        chair.setRoom(room);
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chair.setIsDeleted(chairRequest.getIsDeleted());
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        chair.setIsDeleted(!chair.getIsDeleted());
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chairRepository.save(chair);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Chair> list = chairRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        chairRepository.deleteAll(list);
    }

}
