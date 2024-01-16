package booking_movie.service.movice;
import booking_movie.constants.MovieStatus;
import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.entity.Genre;
import booking_movie.entity.Movie;
import booking_movie.exception.GenreException;
import booking_movie.exception.MovieException;
import booking_movie.mapper.MovieMapper;
import booking_movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private  final MovieRepository movieRepository;
    private  final MovieMapper movieMapper;
    @Override
    public Page<MovieResponseDto> getAllMovie(String keySearch, Pageable pageable) {
        Page<Movie> listMovie;
        if(keySearch==null){
            listMovie = movieRepository.findAllByIsDeleted(pageable,false);
        }else {
            listMovie = movieRepository.findAllByMovieNameContainingIgnoreCaseAndIsDeleted(pageable,keySearch,false);
        }
        return listMovie.map(movieMapper::toResponseDto);
    }
    @Override
    public MovieResponseDto createMovie(MovieRequestDto movieRequestDto) throws MovieException {
        validateMovieRequest(movieRequestDto);
        List<Movie> genreList = movieRepository.findAllByIsDeleted(false);
        System.out.println(genreList);
        if (genreList.stream().anyMatch(item -> item.getMovieName().equals(movieRequestDto.getMovieName()))) {
            throw new GenreException("Duplicate movie");
        }
          Movie movie=   movieMapper.toEntity(movieRequestDto);

       return movieMapper.toResponseDto(movieRepository.save(movie));
    }

    @Override
    public Page<MovieResponseDto> getAllMovieByMovieStatus(String keySearch, Pageable pageable, String movieStatus) throws MovieException {
        MovieStatus status;
        switch (movieStatus.toUpperCase()) {
            case "SHOWING":
                status = MovieStatus.SHOWING;
                break;
            case "UPCOMING":
                status = MovieStatus.UPCOMING;
                break;
            case "STOPPED":
                status = MovieStatus.STOPPED;
                break;
            default:
                throw new MovieException("Invalid movie status");
        }

        Page<Movie> moviePage= movieRepository.findAllByMovieNameContainingIgnoreCaseAndIsDeletedAndMovieStatus(
                pageable, keySearch, false, status);
        return moviePage.map(movieMapper::toResponseDto);
    }


    @Override
    public void deleteMovie(Long idDelete) throws MovieException {
        Movie movieDelete = movieRepository.findMovieByIdAndIsDeleted(idDelete,false);
        if(movieDelete ==null){
            throw new MovieException("movie not found");
        }else {
            movieDelete.setIsDeleted(true);
            movieRepository.save(movieDelete);
        }
    }

    @Override
    public MovieResponseDto updateMovie(MovieRequestDto movieRequestDto, Long idEdit) throws MovieException {
        validateMovieRequest(movieRequestDto);
        Movie movieEdit = movieRepository.findMovieByIdAndIsDeleted(idEdit,false);
        if(movieEdit ==null){
            throw new MovieException("movie not found");
        }else {
            movieEdit=  movieMapper.toEntity(movieRequestDto);
              movieEdit.setId(idEdit);
            movieRepository.save(movieEdit);
            return    movieMapper.toResponseDto(movieEdit) ;
        }

    }

    private void validateMovieRequest(MovieRequestDto movieRequestDto) throws MovieException {
        LocalDateTime currentDate = LocalDateTime.now();
        if (movieRequestDto.getReleaseDate() != null && movieRequestDto.getReleaseDate().isBefore(ChronoLocalDate.from(currentDate))) {
            throw new MovieException("Release date must be in the present or future");
        }

        if (movieRequestDto.getStopDate() != null && movieRequestDto.getStopDate().isBefore(movieRequestDto.getReleaseDate())) {
            throw new MovieException("Stop date must be after release date");
        }
    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateMovieStatus() {
        List<Movie> allMovies = movieRepository.findAllByIsDeleted(false);
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Movie movie : allMovies) {
            // Kiểm tra và chuyển trạng thái dựa trên thời gian chiếu và ngày kết thúc
            if (movie.getReleaseDate() != null && movie.getStopDate() != null) {
                LocalDateTime endDatePlusOneDay = movie.getStopDate().plusDays(1).atStartOfDay(); // Ngày kết thúc + 1 ngày để bao gồm cả ngày đó

                if (currentDateTime.isAfter(movie.getReleaseDate().atStartOfDay()) && currentDateTime.isBefore(endDatePlusOneDay)) {
                    movie.setMovieStatus(MovieStatus.SHOWING);
                } else if (currentDateTime.isAfter(endDatePlusOneDay)) {
                    movie.setMovieStatus(MovieStatus.UPCOMING);
                } else {
                    movie.setMovieStatus(MovieStatus.STOPPED);
                }
            }
        }
    }
}