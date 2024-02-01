package booking_movie.service.theater;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeRequest;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface TheaterService {
    Page<TheaterResponseDto> findAll(String search , Pageable pageable) ;
    List<TheaterResponseDto> finAllNoSearch();

    TheaterResponseDto findById ( Long id ) throws CustomsException;

    TheaterResponseDto save(Authentication authentication, TheaterRequestDto theaterRequestDto) throws CustomsException;

    TheaterResponseDto update(Authentication authentication,Long id , TheaterRequestDto theaterRequestDto) throws CustomsException;

    void isDelete(Authentication authentication,Long id) throws CustomsException;

    void delete() ;

    List<TheaterResponseDto> findByMovieAndDateBookingAndLocationAndType(Long idMovie, DateTimeAndLocationAndTypeRequest request) throws CustomsException;

}
