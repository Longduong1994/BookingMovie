package booking_movie.service.genre;

import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;
import booking_movie.exception.GenreException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    Page<GenreResponseDto> getAllGenre(String keyGenre,Pageable pageable);
    GenreResponseDto createGenre(GenreRequestDto genreRequestDto);
    void deleteGenre (Long idGenre) throws GenreException;
    GenreResponseDto getGenreById(Long idGenre) throws GenreException;

    GenreResponseDto updateGenre (GenreRequestDto genreRequestDto,Long idGenre) throws GenreException;
}
