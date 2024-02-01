package booking_movie.service.theater;

import booking_movie.constants.RoomType;
import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeRequest;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.entity.Location;
import booking_movie.entity.Movie;
import booking_movie.entity.Theater;
import booking_movie.exception.CustomsException;
import booking_movie.mapper.TheaterMapper;
import booking_movie.repository.LocationRepository;
import booking_movie.repository.MovieRepository;
import booking_movie.repository.TheaterRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TheaterServiceImpl implements TheaterService {
    private TheaterRepository theaterRepository ;
    private TheaterMapper theaterMapper ;
    private LocationRepository locationRepository ;
    private MovieRepository movieRepository ;

    @Override
    public Page<TheaterResponseDto> findAll(String search, Pageable pageable) {
        Page<Theater> theaterPage;
        if (search.isEmpty()) {
            theaterPage = theaterRepository.findAllByIsDeletedFalse( pageable);
        } else {
            theaterPage = theaterRepository.findAllByTheaterNameContainingIgnoreCaseAndIsDeletedFalse(search,  pageable);
        }
        return theaterPage.map(item -> theaterMapper.toResponse(item));
    }

    @Override
    public List<TheaterResponseDto> finAllNoSearch() {
        List<Theater> list = theaterRepository.findAll();
        return list.stream().map(item -> theaterMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public TheaterResponseDto findById(Long id) throws CustomsException {
        Theater theater = theaterRepository.findById(id).orElseThrow(()-> new CustomsException("Rạp chiếu không tồn tại")) ;
        return theaterMapper.toResponse(theater);
    }

    @Override
    public TheaterResponseDto save(Authentication authentication, TheaterRequestDto theaterRequestDto) throws CustomsException{
        if (theaterRepository.existsByTheaterName(theaterRequestDto.getTheaterName())){
            throw new CustomsException("Tên rạp chiếu đã tồn tại") ;
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Theater theater  =theaterMapper.toEntity(theaterRequestDto);
        theater.setCreateTime(LocalDateTime.now());
        theater.setUpdateTime(LocalDateTime.now());
        theater.setCreateUser(userPrincipal.getUsername());
        theaterRepository.save(theater);
        return theaterMapper.toResponse(theater);
    }

    @Override
    public TheaterResponseDto update(Authentication authentication,Long id, TheaterRequestDto theaterRequestDto) throws CustomsException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new CustomsException("Rạp chiếu không tồn tại"));
        Location location = locationRepository.findById(theaterRequestDto.getLocationId()).orElseThrow(() -> new CustomsException("Vị trí không tồn tại")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        theater.setId(id);
        theater.setTheaterName(theaterRequestDto.getTheaterName());
        theater.setLocation(location);
        theater.setCreateTime(LocalDateTime.now());
        theater.setUpdateTime(LocalDateTime.now());
        theater.setUpdateUser(userPrincipal.getUsername());
        theater.setIsDeleted(theaterRequestDto.getIsDeleted());
        theaterRepository.save(theater);
        return theaterMapper.toResponse(theater);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new CustomsException("Rạp chiếu không tồn tại"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        theater.setIsDeleted(true);
        theater.setUpdateTime(LocalDateTime.now());
        theater.setUpdateUser(userPrincipal.getUsername());
        theaterRepository.save(theater);

    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Theater> list = theaterRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        theaterRepository.deleteAll(list);
    }

    @Override
    public List<TheaterResponseDto> findByMovieAndDateBookingAndLocationAndType(Long idMovie, DateTimeAndLocationAndTypeRequest request) throws CustomsException {
        Movie movie = movieRepository.findById(idMovie).orElseThrow(()-> new CustomsException("Phim không tồn tại"));
        Location location = locationRepository.findById(request.getIdLocation()).orElseThrow(()-> new CustomsException("Vị trí không tồn tại"));
        RoomType roomType = switch (request.getType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException(request.getType() + " không tồn tại");
        };
        if (request.getDateBooking() == null) {
            request.setDateBooking(LocalDate.now());
        }
        List<Theater> list = theaterRepository.findByMovieAndDateBookingAndLocationAndType(idMovie, request.getDateBooking(), request.getIdLocation(), roomType);
        return list.stream().map(item -> theaterMapper.toResponse(item)).collect(Collectors.toList());
    }
}
