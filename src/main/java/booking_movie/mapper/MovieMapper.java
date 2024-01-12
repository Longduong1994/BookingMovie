package booking_movie.mapper;

import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MovieMapper {

    MovieResponseDto toResponseDto(Movie movie);
    Movie toEntity(MovieRequestDto movieRequestDto);
}
