package booking_movie.mapper;

import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toEntity(RegisterRequestDto requestDto);

    User toEntity(LoginRequestDto loginRequestDto);

    UserResponseDto toResponseDto(User user);
}
