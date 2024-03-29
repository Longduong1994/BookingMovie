package booking_movie.service.chair;

import booking_movie.constants.ChairType;
import booking_movie.constants.RoomType;
import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.request.ChairTypeRequest;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeAndTimeSlotRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.entity.*;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.ChairMapper;
import booking_movie.repository.*;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChairServiceImpl implements ChairService{
    private ChairRepository chairRepository ;
    private RoomRepository roomRepository ;
    private ChairMapper chairMapper ;
    private LocationRepository locationRepository ;
    private MovieRepository movieRepository ;
    private TimeSlotRepository timeSlotRepository ;

    @Override
    public List<ChairResponseDto> findAll() {
        List<Chair> list = chairRepository.findAllByIsDeletedFalse() ;
        return list.stream().map(item -> chairMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public ChairResponseDto findById(Long id) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Ghế không tồn tại")) ;
        return chairMapper.toResponse(chair);
    }

    @Override
    public ChairResponseDto changeChairType(Authentication authentication, Long id, ChairTypeRequest chairTypeRequest) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Ghế không tồn tại")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        switch (chairTypeRequest.getChairType()) {
            case "normal":
                chair.setChairType(ChairType.NORMAL);
                break;
            case "VIP":
                chair.setChairType(ChairType.VIP);
                break;
            default:
                throw new CustomsException("Kiểu ghế không tồn tại");
        }
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }


    @Override
    public ChairResponseDto update(Authentication authentication,Long id, ChairRequest chairRequest) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Ghế không tồn tại")) ;
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomsException("Phòng chiếu không tồn tại")) ;
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
                throw new CustomsException(chairRequest.getChairType() + " không tồn tại");
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
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Ghế không tồn tại")) ;
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

    @Override
    public List<ChairResponseDto> findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(Long idMovie, DateTimeAndLocationAndTypeAndTimeSlotRequest request) throws CustomsException {
        Movie movie = movieRepository.findById(idMovie).orElseThrow(()-> new CustomsException("Phim không tồn tại"));
        Location location = locationRepository.findById(request.getIdLocation()).orElseThrow(() -> new CustomsException("Vị trí không tồn tại"));
        TimeSlot timeSlot = timeSlotRepository.findById(request.getIdTimeSlot()).orElseThrow(()-> new CustomsException("Xuất chiếu không tồn tại"));
        RoomType roomType = switch (request.getType()){
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new  CustomsException(request.getType() + " không tồn tại");
        };
        if (request.getDateBooking() == null) {
            request.setDateBooking(LocalDate.now());
        }
        List<Chair> list = chairRepository.findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(idMovie, request.getDateBooking(), request.getIdLocation(), roomType, request.getIdTimeSlot());
        return list.stream().map(item -> chairMapper.toResponse(item)).collect(Collectors.toList());
    }

}
