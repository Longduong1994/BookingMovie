package booking_movie.service.room;

import booking_movie.constants.ChairType;
import booking_movie.constants.RoomType;
import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.request.RoomUpdateRequestDto;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeAndTimeSlotRequest;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.entity.*;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.RoomMapper;
import booking_movie.repository.*;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository ;
    private RoomMapper roomMapper;
    private ChairRepository chairRepository ;
    private TheaterRepository theaterRepository ;
    private LocationRepository locationRepository;
    private TimeSlotRepository timeSlotRepository ;
    private MovieRepository movieRepository ;

    @Override
    public Page<RoomResponseDto> findAll(String search, Pageable pageable) {
        Page<Room> roomPage ;
        if (search.isEmpty()) {
            roomPage = roomRepository.findAllByIsDeletedFalse( pageable) ;
        } else  {
            roomPage = roomRepository.findAllByRoomNameContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
        }
        return roomPage.map(item -> roomMapper.toResponse(item));
    }

    @Override
    public RoomResponseDto findById(Long id) throws CustomsException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomsException("Room Not Found")) ;
        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponseDto save(Authentication authentication, RoomRequestDto roomRequestDto) throws  CustomsException {
        if (roomRepository.existsByRoomNameAndTheaterId(roomRequestDto.getRoomName(), roomRequestDto.getTheaterId())){
            throw new CustomsException("Exits RoomName") ;
        }
        if (roomRequestDto.getNumberOfSeatsInARow() < 0 && roomRequestDto.getNumberOfSeatsInAColumn() < 0 ) {
            throw new CustomsException("The number of seats cannot be negative") ;
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int numberOfRows = Math.min(roomRequestDto.getNumberOfSeatsInARow(), string.length());

        Room room = roomMapper.toEntity(roomRequestDto);
        room.setCreateTime(LocalDateTime.now());
        room.setUpdateTime(LocalDateTime.now());
        room.setCreateUser(userPrincipal.getUsername());
        roomRepository.save(room);



        for (int i = 0; i < numberOfRows; i++) {
            String chairNameOf = String.valueOf(string.charAt(i));
            for (int j = 0; j < roomRequestDto.getNumberOfSeatsInAColumn(); j++) {
                Chair chair = new Chair();
                chair.setChairName(chairNameOf + (j + 1));
                // Sử dụng điều kiện để xác định loại ghế
                chair.setChairType(i < 5 ? ChairType.NORMAL : ChairType.VIP);
                chair.setRoom(room);
                chair.setIsDeleted(false);
                chair.setCreateTime(LocalDateTime.now());
                chair.setUpdateTime(LocalDateTime.now());
                chair.setCreateUser(userPrincipal.getUsername());
                chairRepository.save(chair);
            }
        }
        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponseDto update(Authentication authentication,Long id, RoomUpdateRequestDto roomUpdateRequestDto) throws CustomsException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomsException("Room Not Found"));
        Theater theater = theaterRepository.findById(roomUpdateRequestDto.getTheaterId()).orElseThrow(()-> new CustomsException("Theater Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        RoomType roomType = switch (roomUpdateRequestDto.getRoomType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException("RoomType Not Found");
        };
        room.setId(id);
        room.setRoomName(roomUpdateRequestDto.getRoomName());
        room.setRoomType(roomType);
        room.setTheater(theater);
        room.setUpdateTime(LocalDateTime.now());
        room.setUpdateUser(userPrincipal.getUsername());
        roomRepository.save(room);
        return roomMapper.toResponse(room);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomsException("Room Not Found"));
        room.setIsDeleted(!room.getIsDeleted());
        room.setUpdateTime(LocalDateTime.now());
        room.setUpdateUser(userPrincipal.getUsername());
        roomRepository.save(room);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Room> list = roomRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        roomRepository.deleteAll(list);
    }

    @Override
    public RoomResponseDto findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(Long idMovie, DateTimeAndLocationAndTypeAndTimeSlotRequest request) throws CustomsException {
        Movie movie = movieRepository.findById(idMovie).orElseThrow(()-> new CustomsException("Movie Not Found"));
        Location location = locationRepository.findById(request.getIdLocation()).orElseThrow(()-> new CustomsException("Location Not Found"));
        TimeSlot timeSlot = timeSlotRepository.findById(request.getIdTimeSlot()).orElseThrow(()-> new CustomsException("TimeSlot Not Found"));
        RoomType roomType = switch (request.getType()){
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new  CustomsException(request.getType() + " Not Found");
        };
        if (request.getDateBooking() == null) {
            request.setDateBooking(LocalDate.now());
        }
        Room room = roomRepository.findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(idMovie,request.getDateBooking(), request.getIdLocation(), roomType, request.getIdTimeSlot());
        return roomMapper.toResponse(room);
    }
}
