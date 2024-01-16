package booking_movie.service.theater;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.exception.LocationException;
import booking_movie.exception.TheaterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TheaterService {
    Page<TheaterResponseDto> findAll(String search , Pageable pageable) ;

    TheaterResponseDto findById ( Long id ) throws TheaterException;

    TheaterResponseDto save(TheaterRequestDto theaterRequestDto) throws TheaterException, LocationException;

    TheaterResponseDto update(Long id , TheaterRequestDto theaterRequestDto) throws TheaterException, LocationException;

    void delete(Long id) throws TheaterException;

}
