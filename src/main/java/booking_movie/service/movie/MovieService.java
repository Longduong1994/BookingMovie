package booking_movie.service.movie;

import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.request.MovieUpdateRequestDto;
import booking_movie.dto.response.MovieResponseDto;

import booking_movie.entity.Movie;
import booking_movie.exception.LoginException;
import booking_movie.exception.MovieException;
import booking_movie.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MovieService {
    Page<MovieResponseDto> getAllMovieByMovieStatus(String keySearch, Pageable pageable, String movieStatus)throws MovieException;
    Page<MovieResponseDto> getAllMovie(String keySearch, Pageable pageable);
    List<MovieResponseDto> findAll();
    MovieResponseDto createMovie(MovieRequestDto movieRequestDto, Authentication authentication) throws MovieException, LoginException;

    void deleteMovie(Long idDelete) throws MovieException;
    MovieResponseDto updateMovie(MovieUpdateRequestDto movieRequestDto, Authentication authentication , Long idEdit) throws MovieException, LoginException;

    MovieResponseDto getMovieById(Long idMovie) throws MovieException;

    Double countMovie(Authentication authentication) throws UserException;
}
