package booking_movie.mapper;
import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;;
import booking_movie.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GenreMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "isDeleted",ignore = true)
    GenreResponseDto toResponseDto(Genre genre);
    @Mapping(target = "isDeleted",ignore = true)
    Genre toEntity(GenreRequestDto genreRequestDto);
}
