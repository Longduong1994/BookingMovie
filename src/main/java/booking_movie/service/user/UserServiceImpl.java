package booking_movie.service.user;

import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.exception.LoginException;
import booking_movie.exception.RegisterException;
import booking_movie.mapper.UserMapper;
import booking_movie.repository.UserRepository;
import booking_movie.service.mail.MailService;
import booking_movie.security.jwt.JwtProvider;
import booking_movie.security.user_principle.UserPrincipal;
import booking_movie.service.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final AuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;



    @Override
    public String register(RegisterRequestDto registerRequestDto) throws RegisterException {
        if (userRepository.existsByUsername(registerRequestDto.getUsername()))
            throw new RegisterException("Username already exists");
        if (userRepository.existsByEmail(registerRequestDto.getEmail()))
            throw new RegisterException("Email already exists");
        if (userRepository.existsByPhone(registerRequestDto.getPhone()))
            throw new RegisterException("Phone already exists");
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleCustomer());
        User user = userMapper.toEntity(registerRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCard(UUID.randomUUID().toString().substring(0, 9));
        user.setRoles(roles);
        user.setStatus(true);
        userRepository.save(user);
        String emailContent = """
        <p style="color: red; font-size: 18px;">
        Registered successfully
        </p>
        """;
        mailService.sendMail(registerRequestDto.getEmail(), "RegisterSuccess", emailContent);
        return "Register successfully";
    }

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) throws LoginException {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        } catch (AuthenticationException ex) {
            throw new LoginException("username or password invalid");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUser().getStatus()) {
            throw new LoginException("Account is locked");
        }
        String token = jwtProvider.generateToken(userPrincipal);
        UserResponseDto userResponse = userMapper.toResponseDto(userPrincipal.getUser());
        userResponse.setToken(token);
        userResponse.setSetRoles(userPrincipal.getUser().getRoles().stream().map(item -> item.getRoleName().name()).collect(Collectors.toSet()));
        return userResponse;
    }

    @Override
    public String createAccountCustomer(CreateAccountDto createAccountDto, Authentication authentication) throws LoginException {
        User user = getUser(authentication);
        return null;
    }

    @Override
    public User getUser(Authentication authentication) throws LoginException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new LoginException("You must log in to use the service");
        }
        return userPrincipal.getUser();
    }


}
