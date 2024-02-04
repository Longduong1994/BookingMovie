package booking_movie.service.user;

import booking_movie.dto.request.*;
import booking_movie.dto.response.*;
import booking_movie.entity.User;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.PromtionException;
import booking_movie.exception.RegisterException;
import booking_movie.exception.UserException;
import booking_movie.security.user_principle.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface UserService {

    String changePassword(ChangePasswordDto changePasswordDto,Authentication authentication) throws LoginException, CustomsException;
    UserProfileDto profile(Authentication authentication) throws LoginException;
    UserProfileDto uploadAvatar(Authentication authentication,AvatarUploadDto avatarUploadDto) throws LoginException;
    Page<CustomerResponse> findAllCustomer(int page,String username);
    Page<ManagerResponse> findAllManager(int page, int size, String username);
    Page<EmployerResponse> findAllEmployer(int page, int size, String username);
    String register( RegisterRequestDto registerRequestDto) throws RegisterException;
    UserResponseDto login(LoginRequestDto loginRequestDto) throws LoginException;
    String createAccount(CreateAccountDto createAccountDto, Authentication authentication) throws LoginException, RegisterException, NotFoundException;
    User getUser(Authentication authentication) throws LoginException;
    String changeStatus(Long id,Authentication authentication) throws LoginException;
    /**
     * find By id user
     * return {@Link User}
     *
     * @author huyqt97
     */
    public User userById(Authentication authentication)throws UserException;

    public String setRole(Authentication authentication,String roleName,Long id) throws LoginException, CustomsException, NotFoundException;

    void registerAdmin(CreateAccountDto createAccountDto) throws CustomsException;
    CustomerResponse updateCustomer(Authentication authentication, UpdateUserDto updateUserDto) throws CustomsException, LoginException;
    String getLink(PasswordRetrievalDto passwordRetrievalDto) throws CustomsException;
    String retrievalPassword(NewPasswordDto newPasswordDto,String email) throws NotFoundException, CustomsException;
    Integer sumCustomer(Authentication authentication) throws UserException;
}
