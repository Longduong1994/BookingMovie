package booking_movie.service.timeSlot;

import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.dto.response.TimeSlotResponseDto;
import booking_movie.entity.Movie;
import booking_movie.entity.Room;
import booking_movie.entity.TimeSlot;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.TimeSlotMapper;
import booking_movie.repository.MovieRepository;
import booking_movie.repository.RoomRepository;
import booking_movie.repository.TimeSlotRepository;
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
public class TimeSlotServiceImpl implements TimeSlotService {
    private TimeSlotRepository timeSlotRepository ;
    private MovieRepository movieRepository ;
    private RoomRepository roomRepository ;
    private TimeSlotMapper timeSlotMapper ;

    @Override
    public List<TimeSlotResponseDto> findAll() {
        List<TimeSlot> list = timeSlotRepository.findAllByIsDeletedFalse();
        return list.stream().map(item -> timeSlotMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public TimeSlotResponseDto findById(Long id) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("TimeSlot Not Found"));
        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public TimeSlotResponseDto save(Authentication authentication , TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
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
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("TimeSlot Not Found"));
        Movie movie = movieRepository.findById(timeSlotRequestDto.getMovieId()).orElseThrow(() -> new CustomsException("Movie Not Found"));
        Room room = roomRepository.findById(timeSlotRequestDto.getRoomId()).orElseThrow(() -> new CustomsException("Room Not Found"));
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
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("TimeSlot Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setIsDeleted(!timeSlot.getIsDeleted());
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
}
