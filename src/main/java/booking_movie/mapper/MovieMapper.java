package booking_movie.mapper;
import booking_movie.constants.MovieStatus;
import booking_movie.constants.RoomType;
import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.entity.Format;
import booking_movie.entity.Genre;
import booking_movie.entity.Movie;
import booking_movie.exception.CustomsException;
import booking_movie.repository.FormatRepository;
import booking_movie.repository.GenreRepository;
import booking_movie.service.upload_image.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Component
@AllArgsConstructor
public class MovieMapper {
    private  final GenreRepository genreRepository;
    private  final UploadFileService uploadFileService;
    private final FormatRepository formatRepository;
    public MovieResponseDto toResponseDto(Movie movie) {
        Set<String> genreNames = movie.getGenres().stream()
                .map(Genre::getGenreName)
                .collect(Collectors.toSet());
        Set<Long> genreIDs = movie.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
        return MovieResponseDto.builder()
                .id(movie.getId())
                .movieImage(movie.getMovieImage())
                .movieName(movie.getMovieName())
                .price(movie.getPrice())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .description(movie.getDescription())
                .runningTime(movie.getRunningTime())
                .releaseDate(movie.getReleaseDate())
                .stopDate(movie.getStopDate())
                .language(movie.getLanguage())
                .rated(movie.getRated())
                .movieStatus(movie.getMovieStatus())
                .genreName(genreNames)
                .genreId(genreIDs)
                .build();
    }
    public Movie toEntity(MovieRequestDto movieRequestDto)  {
        Set<Genre> genres = movieRequestDto.getGenreId().stream()
                .map(item -> genreRepository.findGenreByIdAndIsDeleted(item,false))
                .collect(Collectors.toSet());
//        Set<Format> formats = movieRequestDto.getFormats().stream()
//                .map(item -> formatRepository.findFormatById(item))
//                .collect(Collectors.toSet());

        Movie movie= Movie.builder()
                .movieName(movieRequestDto.getMovieName())
                .movieImage(uploadFileService.uploadFile(movieRequestDto.getMovieImage()))
                .director(movieRequestDto.getDirector())
                .price(movieRequestDto.getPrice())
                .cast(movieRequestDto.getCast())
                .description(movieRequestDto.getDescription())
                .runningTime(movieRequestDto.getRunningTime())
                .releaseDate(movieRequestDto.getReleaseDate())
                .stopDate(movieRequestDto.getStopDate())
                .language(movieRequestDto.getLanguage())
//                .rated(movieRequestDto.getRated())
//                .formats(formats)
                .genres(genres)
                .movieStatus(MovieStatus.SHOWING)
                .build();
        movie.setIsDeleted(false);
        return movie;
    }
}
