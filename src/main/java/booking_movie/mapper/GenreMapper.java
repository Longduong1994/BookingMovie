package booking_movie.mapper;
import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;;
import booking_movie.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GenreMapper {
    GenreResponseDto toResponseDto(Genre genre);
    Genre toEntity(GenreRequestDto genreRequestDto);
}
