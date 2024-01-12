package booking_movie.service.user;

import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.User;
import booking_movie.exception.LoginException;
import booking_movie.exception.RegisterException;
import org.springframework.security.core.Authentication;

public interface UserService {
    String register(RegisterRequestDto registerRequestDto) throws RegisterException;

    UserResponseDto login(LoginRequestDto loginRequestDto) throws LoginException;

    String createAccountCustomer(CreateAccountDto createAccountDto, Authentication authentication) throws LoginException;

    User getUser(Authentication authentication) throws LoginException;

}
