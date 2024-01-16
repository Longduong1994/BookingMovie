package booking_movie.mapper;

import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MovieMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "formats", source = "formats")
    MovieResponseDto toResponseDto(Movie movie);
    @Mapping(target = "formats", source = "formats")

    Movie toEntity(MovieRequestDto movieRequestDto);
}
