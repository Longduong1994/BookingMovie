package booking_movie.mapper;
import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;;
import booking_movie.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GenreMapper {
    GenreResponseDto toResponseDto(Genre genre);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDttm", ignore = true)
    @Mapping(target = "updateDttm", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateClass", ignore = true)
    Genre toEntity(GenreRequestDto genreRequestDto);
}
