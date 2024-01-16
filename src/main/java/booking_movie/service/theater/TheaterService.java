package booking_movie.service.theater;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.dto.response.TheaterResponseDto;
import booking_movie.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TheaterService {
    Page<TheaterResponseDto> findAll(String search , Pageable pageable) ;

    TheaterResponseDto findById ( Long id ) throws CustomsException;

    TheaterResponseDto save(Authentication authentication, TheaterRequestDto theaterRequestDto) throws CustomsException;

    TheaterResponseDto update(Authentication authentication,Long id , TheaterRequestDto theaterRequestDto) throws CustomsException;

    void isDelete(Authentication authentication,Long id) throws CustomsException;

    void delete() ;

}
