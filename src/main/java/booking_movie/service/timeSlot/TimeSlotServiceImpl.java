package booking_movie.service.timeSlot;

import booking_movie.constants.RoomType;
import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeRequest;
import booking_movie.dto.response.TimeSlotResponseDto;
import booking_movie.entity.*;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.TimeSlotMapper;
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
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    @Override
    public Page<TimeSlotResponseDto> findALl(String search, Pageable pageable) {
        Page<TimeSlot> page ;
        if (search.isEmpty()) {
            page = timeSlotRepository.findAllByIsDeletedFalse(pageable);
        } else {
            page = timeSlotRepository.findAllByStartTimeAndIsDeletedFalse(LocalTime.parse(search), pageable);
        }
        return page.map(item -> {
            try {
                return timeSlotMapper.toResponse(item);
            } catch (CustomsException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private TimeSlotRepository timeSlotRepository ;
    private MovieRepository movieRepository ;
    private TheaterRepository theaterRepository ;
    private LocationRepository locationRepository ;
    private RoomRepository roomRepository ;
    private TimeSlotMapper timeSlotMapper ;

    @Override
    public List<TimeSlotResponseDto> findAllNoSearch() {
        List<TimeSlot> list = timeSlotRepository.findAllByIsDeletedFalse();
        return list.stream().map(item -> {
            try {
                return timeSlotMapper.toResponse(item);
            } catch (CustomsException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public TimeSlotResponseDto findById(Long id) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("Xuất chiếu không tồn tại"));
        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public TimeSlotResponseDto save(Authentication authentication , TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
        if (timeSlotRepository.existsByRoomTheaterIdAndRoomIdAndMovieIdAndStartTime(timeSlotRequestDto.getTheaterId(), timeSlotRequestDto.getRoomId(), timeSlotRequestDto.getMovieId(), timeSlotRequestDto.getStartTime())){
            throw new CustomsException("Xuất chiếu đã tồn tại");
        }
        TimeSlot timeSlot = timeSlotRepository.save(timeSlotMapper.toEntity(timeSlotRequestDto));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setCreateTime(LocalDateTime.now());
        timeSlot.setUpdateTime(LocalDateTime.now());
        timeSlot.setCreateUser(userPrincipal.getUsername());
        TimeSlot create = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(create);
    }

    @Override
    public TimeSlotResponseDto update(Authentication authentication ,Long id, TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("Xuất chiếu không tồn tại"));
        Movie movie = movieRepository.findById(timeSlotRequestDto.getMovieId()).orElseThrow(() -> new CustomsException("Phim không tồn tại"));
        Room room = roomRepository.findById(timeSlotRequestDto.getRoomId()).orElseThrow(() -> new CustomsException("Phòng chiếu không tồn tại"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setId(id);
        timeSlot.setMovie(movie);
        timeSlot.setRoom(room);
        timeSlot.setStartTime(timeSlotRequestDto.getStartTime());
        timeSlot.setUpdateTime(LocalDateTime.now());
        timeSlot.setUpdateUser(userPrincipal.getUsername());
        timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public void isDelete(Authentication authentication, Long id) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("Xuất chiếu không tồn tại"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setIsDeleted(true);
        timeSlot.setUpdateTime(LocalDateTime.now());
        timeSlot.setUpdateUser(userPrincipal.getUsername());
        timeSlotRepository.save(timeSlot);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<TimeSlot> listTime = timeSlotRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        timeSlotRepository.deleteAll(listTime);

        List<TimeSlot> listTimeRoom = timeSlotRepository.findAllByRoomIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        timeSlotRepository.deleteAll(listTimeRoom);

        List<TimeSlot> listTimeMovie = timeSlotRepository.findAllByMovieIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        timeSlotRepository.deleteAll(listTimeMovie);
    }

    @Override
    public List<TimeSlotResponseDto> findAllByIdMovieAndDateBookingAndIdLocationAndTypeAndIdTheater(Long idMovie, DateTimeAndLocationAndTypeRequest request) throws CustomsException {
        Movie movie = movieRepository.findById(idMovie).orElseThrow(() -> new CustomsException("Phim không tồn tại"));
        Location location = locationRepository.findById(request.getIdLocation()).orElseThrow(()-> new CustomsException("Vị trí không tồn tại"));
        Theater theater = theaterRepository.findById(request.getIdLocation()).orElseThrow(() -> new CustomsException("Rạp chiếu không tồn tại")) ;
        RoomType roomType = switch (request.getType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException(request.getType() + " Xuất chiếu không tồn tại") ;
        };
        if (request.getDateBooking() == null) {
            request.setDateBooking(LocalDate.now());
        }

        List<TimeSlot> timeSlot = timeSlotRepository.findAllByIdMovieAndDateBookingAndIdLocationAndTypeAndIdTheater(idMovie, request.getDateBooking(), request.getIdLocation(), roomType);
       return timeSlot.stream().map(item -> {
           try {
               return timeSlotMapper.toResponse(item);
           } catch (CustomsException e) {
               throw new RuntimeException(e);
           }
       }).collect(Collectors.toList());
    }
}
