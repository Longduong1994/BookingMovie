package booking_movie.service.genre;

import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.GenreException;
import booking_movie.exception.LoginException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface GenreService {
    Page<GenreResponseDto> getAllGenre(String keyGenre, Pageable pageable);
    List<GenreResponseDto> findAllNoSearch();
    GenreResponseDto findById (Long id) throws CustomsException;
    GenreResponseDto createGenre(GenreRequestDto genreRequestDto, Authentication authentication) throws LoginException;
    void deleteGenre (Long idGenre) throws GenreException;
    GenreResponseDto getGenreById(Long idGenre) throws GenreException;
    GenreResponseDto updateGenre (GenreRequestDto genreRequestDto,Long idGenre,Authentication authentication) throws GenreException, LoginException;
}
