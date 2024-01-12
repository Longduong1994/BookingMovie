package booking_movie.service.user;

import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.exception.RegisterException;
import booking_movie.mapper.UserMapper;
import booking_movie.repository.RoleRepository;
import booking_movie.repository.UserRepository;
import booking_movie.service.mail.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;



    @Override
    public String register(RegisterRequestDto registerRequestDto) throws RegisterException {
        if (userRepository.existsByUsername(registerRequestDto.getUsername()))
            throw new RegisterException("Username already exists");
        if (userRepository.existsByEmail(registerRequestDto.getEmail()))
            throw new RegisterException("Email already exists");
        if (userRepository.existsByPhone(registerRequestDto.getPhone()))
            throw new RegisterException("Phone already exists");
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("CUSTOMER"));
        User user = userMapper.toEntity(registerRequestDto);
        user.setRoles(roles);
        userRepository.save(user);
        String emailContent = "<p style=\"color: red; font-size: 18px;\">\n" +
                "Registered successfully</p>";
        mailService.sendMail(registerRequestDto.getEmail(), "RegisterSuccess", emailContent);
        return "Register successfully";
    }

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
