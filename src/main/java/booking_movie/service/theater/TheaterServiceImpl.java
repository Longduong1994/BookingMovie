package booking_movie.service.theater;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.entity.Location;
import booking_movie.entity.Theater;
import booking_movie.exception.LocationException;
import booking_movie.exception.TheaterException;
import booking_movie.mapper.TheaterMapper;
import booking_movie.repository.LocationRepository;
import booking_movie.repository.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TheaterServiceImpl implements TheaterService {
    private TheaterRepository theaterRepository ;
    private TheaterMapper theaterMapper ;
    private LocationRepository locationRepository ;

    @Override
    public Page<TheaterResponseDto> findAll(String search, Pageable pageable) {
        Page<Theater> theaterPage;
        if (search.isEmpty()) {
            theaterPage = theaterRepository.findAllByIsDeleted(false, pageable);
        } else {
            theaterPage = theaterRepository.findAllByTheaterNameContainingIgnoreCaseAndIsDeleted(search, false, pageable);
        }
        return theaterPage.map(item -> theaterMapper.toResponse(item));
    }

    @Override
    public TheaterResponseDto findById(Long id) throws TheaterException {
        Theater theater = theaterRepository.findById(id).orElseThrow(()-> new TheaterException("Theater Not Found")) ;
        return theaterMapper.toResponse(theater);
    }

    @Override
    public TheaterResponseDto save(TheaterRequestDto theaterRequestDto) throws TheaterException, LocationException {
        if (theaterRepository.existsByTheaterName(theaterRequestDto.getTheaterName())){
            throw new TheaterException("Exits TheaterName") ;
        }
        Location location = locationRepository.findById(theaterRequestDto.getLocationId()).orElseThrow(() -> new LocationException("Location Not Found"));
        Theater theater = theaterRepository.save(theaterMapper.toEntity(theaterRequestDto));
        return theaterMapper.toResponse(theater);
    }

    @Override
    public TheaterResponseDto update(Long id, TheaterRequestDto theaterRequestDto) throws TheaterException, LocationException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new TheaterException("Theater Not Found"));
        Location location = locationRepository.findById(theaterRequestDto.getLocationId()).orElseThrow(() -> new LocationException("Location Not Found")) ;
        theater.setId(id);
        theater.setTheaterName(theaterRequestDto.getTheaterName());
        theater.setLocation(location);
        theater.setIsDeleted(theaterRequestDto.getIsDeleted());
        theaterRepository.save(theater);
        return theaterMapper.toResponse(theater);
    }

    @Override
    public void delete(Long id) throws TheaterException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new TheaterException("Theater Not Found"));
        theater.setIsDeleted(!theater.getIsDeleted());
        theaterRepository.save(theater);

    }
}
