package booking_movie.service.movice;
import booking_movie.constants.MovieStatus;
import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.entity.Movie;
import booking_movie.exception.MovieException;
import booking_movie.mapper.MovieMapper;
import booking_movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
            listMovie = movieRepository.findAllByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(pageable,keySearch,false);
        }
        return listMovie.map(movieMapper::toResponseDto);
    }
    @Override
    public MovieResponseDto createMovie(MovieRequestDto movieRequestDto) {
          Movie movie=   movieMapper.toEntity(movieRequestDto);
       return movieMapper.toResponseDto(movieRepository.save(movie));
    }

    @Override
    public Page<Movie> getAllMovieByMovieStatus(String keySearch, Pageable pageable, String movieStatus) throws MovieException {
        switch (movieStatus) {
            case "SHOWING":
                return movieRepository.findAllByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeletedAndMovieStatus(
                        pageable, keySearch, false,MovieStatus.SHOWING);
            case "UPCOMING":
                return movieRepository.findAllByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeletedAndMovieStatus(
                        pageable, keySearch, false, MovieStatus.UPCOMING);
            case "STOPPED":
                return movieRepository.findAllByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeletedAndMovieStatus(
                        pageable, keySearch, false, MovieStatus.STOPPED);
            default:
                throw new MovieException("movie not found");
        }
    }

    @Override
    public void deleteMovie(Long idDelete) {
        Movie movie = movieRepository.findMovieByIsDeleted(false);

    }

}
