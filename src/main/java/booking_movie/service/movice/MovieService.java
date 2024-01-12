package booking_movie.service.movice;

import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.entity.Movie;
import booking_movie.exception.MovieException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface MovieService {
    Page<MovieResponseDto> getAllMovie(String keySearch, Pageable pageable);
    MovieResponseDto createMovie(MovieRequestDto movieRequestDto);
    Page<Movie> getAllMovieByMovieStatus(String keySearch, Pageable pageable, String movieStatus) throws MovieException;

    void deleteMovie(Long idDelete);
}
