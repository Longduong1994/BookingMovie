package booking_movie.mapper;


import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(RegisterRequestDto requestDto);

    User toEntity(CreateAccountDto createAccountDto);

    UserResponseDto toResponseDto(User user);
}
