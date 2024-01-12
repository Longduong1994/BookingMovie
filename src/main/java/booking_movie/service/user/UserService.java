package booking_movie.service.user;

import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.exception.RegisterException;

public interface UserService {
    String register(RegisterRequestDto registerRequestDto) throws RegisterException;

    UserResponseDto login(LoginRequestDto loginRequestDto);

}
