package booking_movie.service.user;

import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.User;
import booking_movie.exception.LoginException;
import booking_movie.exception.PromtionException;
import booking_movie.exception.RegisterException;
import booking_movie.exception.UserException;
import booking_movie.security.user_principle.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface UserService {

    Page<User> findAllCustomer(int page,int size,String username);
    String register(RegisterRequestDto registerRequestDto) throws RegisterException;

    UserResponseDto login(LoginRequestDto loginRequestDto) throws LoginException;

    String createAccountCustomer(CreateAccountDto createAccountDto, Authentication authentication) throws LoginException;

    User getUser(Authentication authentication) throws LoginException;

    String changeStatus(Long id,Authentication authentication) throws LoginException;
    /**
     * find By id user
     * return {@Link User}
     *
     * @author huyqt97
     */
    public User userById(Authentication authentication)throws UserException;
}
