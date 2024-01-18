package booking_movie.service.user;



import booking_movie.constants.RankName;
import booking_movie.dto.request.*;
import booking_movie.dto.response.*;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.PromtionException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.RegisterException;
import booking_movie.exception.UserException;
import booking_movie.mapper.UserMapper;
import booking_movie.repository.TheaterRepository;
import booking_movie.repository.UserRepository;
import booking_movie.service.mail.MailService;
import booking_movie.security.jwt.JwtProvider;
import booking_movie.security.user_principle.UserPrincipal;
import booking_movie.service.role.RoleService;
import booking_movie.service.upload_image.UploadFileService;
import booking_movie.service.verification.VerificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.HashSet;
import java.util.Optional;
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
    private final VerificationService verificationService;
    private final UploadFileService uploadFileService;


    @Override
    public String changeStatus(Long id,Authentication authentication) throws LoginException {
        User user = getUser(authentication);
        if (!roleService.hasRoleAdmin(user)){
            throw new RuntimeException("");
        }
        Optional<User> userChange = userRepository.findById(id);
        if (!userChange.isPresent()){
            throw new RuntimeException("not found");
        }
        userChange.get().setStatus(!userChange.get().getStatus());
        userRepository.save(userChange.get());
        return "Success";
    }


    @Override
    public String createAccount(CreateAccountDto createAccountDto, Authentication authentication) throws RegisterException, LoginException, NotFoundException {
        User user = getUser(authentication);
        Set<Role> roles = new HashSet<>();
        if (userRepository.existsByEmail(createAccountDto.getEmail())) {
            throw new RegisterException("Exist Email");
        }

        if (userRepository.existsByUsername(createAccountDto.getUsername())) {
            throw new RegisterException("Exist UserName");
        }

        if (userRepository.existsByPhone(createAccountDto.getPhone())) {
            throw new RegisterException("Exist Phone");
        }
        if (roleService.hasRoleAdmin(user)) {
            if (createAccountDto.getRole().equals("manager")) {
                roles.add(roleService.getRoleManager());
                roles.add(roleService.getRoleEmployee());
                roles.add(roleService.getRoleCustomer());
            } else if (createAccountDto.getRole().equals("employer")) {
                roles.add(roleService.getRoleManager());
                roles.add(roleService.getRoleEmployee());
            } else if (createAccountDto.getRole().equals("customer")) {
                roles.add(roleService.getRoleCustomer());
            } else {
                throw new NotFoundException("Role not found");
            }
        } else if (roleService.hasRoleManager(user)) {
            if (createAccountDto.getRole().equals("employer")) {
                roles.add(roleService.getRoleManager());
                roles.add(roleService.getRoleEmployee());
            } else if (createAccountDto.getRole().equals("customer")) {
                roles.add(roleService.getRoleCustomer());
            } else {
                throw new NotFoundException("You have no rights");
            }
        } else if (roleService.hasRoleEmployer(user)) {
            if (createAccountDto.getRole().equals("customer")) {
                roles.add(roleService.getRoleCustomer());
            } else {
                throw new NotFoundException("You have no rights");
            }
        }

        User newUser = userMapper.toEntity(createAccountDto);
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setStatus(true);
        userRepository.save(newUser);
        return "success";
    }



    @Override
    public void registerAdmin(CreateAccountDto createAccount) throws CustomsException {
        if (userRepository.existsByEmail(createAccount.getEmail())) {
            throw new CustomsException("Exist Email");
        }
        if (userRepository.existsByUsername(createAccount.getUsername())) {
            throw new CustomsException("Exist UserName");
        }
        if (userRepository.existsByPhone(createAccount.getPhone())) {
            throw new CustomsException("Exist Phone");
        }
        Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRoleAdmin());
            roles.add(roleService.getRoleManager());
            roles.add(roleService.getRoleEmployee());
       User user = userMapper.toEntity(createAccount);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setStatus(true);
       userRepository.save(user);
    }

    @Override
    public String getLink(PasswordRetrievalDto passwordRetrievalDto, HttpSession session) throws CustomsException {
        if (!validateCaptcha(session, passwordRetrievalDto.getCaptcha())) {
            session.removeAttribute("captchaCode");
            throw new CustomsException("Invalid captcha");
        }
        session.removeAttribute("captchaCode");

        String link = "http://localhost:6789/retrieval?email=" + passwordRetrievalDto.getEmail();

        String emailContent = """
        <div style="text-align: center; font-family: Arial, sans-serif; color: #333;">
            <p>
                Cinima.vn đã nhận được yêu cầu thay đổi mật khẩu của quý khách.
            </p>
            <p>
                Xin hãy click vào link "ĐỔI MẬT KHẨU" bên dưới để đổi mật khẩu: (Lưu ý: link chỉ có hiệu lực trong vòng 60 phút.)
            </p>
            <a href="%s" style="text-decoration: none;">
                <button style="background-color: #4CAF50; /* Green */
                                border: none;
                                color: white;
                                padding: 15px 32px;
                                text-align: center;
                                text-decoration: none;
                                display: inline-block;
                                font-size: 16px;
                                margin: 4px 2px;
                                cursor: pointer;
                                border-radius: 5px;">
                    Đổi Mật Khẩu
                </button>
            </a>
            <p>
                Mọi thắc mắc và góp ý vui lòng liên hệ với bộ phận chăm sóc khách hàng:
            </p>
            <p>
                - Hotline: 1900 6017
            </p>
            <p>
                - Giờ làm việc: 8:00 - 22:00
            </p>
        </div>
        """.formatted(link);

        mailService.sendMail(passwordRetrievalDto.getEmail(), "Yêu cầu thay đổi mật khẩu", emailContent);
        return "success";
    }


    @Override
    public String retrievalPassword(NewPasswordDto newPasswordDto,String email) throws NotFoundException, CustomsException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new NotFoundException("Email is incorrect");
        }
        if (!newPasswordDto.getPassword().equals(newPasswordDto.getConfirmPassword())){
            throw new CustomsException("Confirm password was wrong");
        }
        user.get().setPassword(passwordEncoder.encode(newPasswordDto.getPassword()));
        userRepository.save(user.get());
        return "success";
    }

    @Override
    public CustomerResponse updateCustomer(Long id,Authentication authentication, UpdateUserDto updateUserDto) throws CustomsException, LoginException {
        User user = getUser(authentication);
        if (!user.getId().equals(id)) {
            throw new CustomsException("You have no rights");
        }
        user.setAddress(updateUserDto.getAddress());
        user.setCity(updateUserDto.getCity());
        user.setPhone(updateUserDto.getPhone());
        user.setGender(updateUserDto.getGender());
        return userMapper.toCustomer(user);
    }

    private String sendVerification(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String verification = verificationService.create(user.get()).getVerificationCode();
            String content = "Hello " + email + ",\n\n" +
                    "For security reasons, you are required to use the following One Time Password to log in:\n" +
                    "\n" + verification +
                    "\n\nNote: This OTP is set to expire in 5 minutes.\n\n" +
                    "If you did not request this password reset, please contact us immediately at support@example.com.";
            mailService.sendMail(email, "Verification", content);

            return "Log in to gmail to get the confirmation code";
        }
        return "Email address incorrect";
    }

    @Override
    public Page<CustomerResponse> findAllCustomer(int page, int size, String username) {
        Page<User> customers = userRepository.findAllCustomers(username,roleService.getRoleCustomer(),PageRequest.of(page,size));
        return customers.map(userMapper::toCustomer);
    }

    @Override
    public Page<ManagerResponse> findAllManager(int page, int size, String username) {
        Page<User> customers = userRepository.findAllCustomers(username,roleService.getRoleManager(),PageRequest.of(page,size));
        return customers.map(userMapper::toManager);
    }

    @Override
    public Page<EmployerResponse> findAllEmployer(int page, int size, String username) {
        Page<User> customers = userRepository.findAllCustomers(username,roleService.getRoleEmployee(),PageRequest.of(page,size));
        return customers.map(userMapper::toEmployer);
    }


    private boolean validateCaptcha(HttpSession session, String userInput) {
        String storedCaptcha = (String) session.getAttribute("captchaCode");
        return userInput != null && storedCaptcha != null && userInput.equalsIgnoreCase(storedCaptcha);
    }

    @Transactional
    @Override
    public String register(RegisterRequestDto registerRequestDto,HttpSession session) throws RegisterException {
        if (!validateCaptcha(session,registerRequestDto.getCaptcha())) {
            throw new RegisterException("Invalid captcha");
        }
        session.removeAttribute("captchaCode");
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
        user.setPoint(0);
        user.setLevel(RankName.COPPER);
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
    public UserResponseDto login(LoginRequestDto loginRequestDto,HttpSession session) throws LoginException {
        if (!validateCaptcha(session,loginRequestDto.getCaptcha())) {
            session.removeAttribute("captchaCode");
            throw new LoginException("Invalid captcha");
        }
        session.removeAttribute("captchaCode");
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
    public User getUser(Authentication authentication) throws LoginException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (userPrincipal == null) {
            throw new LoginException("You must log in to use the service");
        }
        return userPrincipal.getUser();
    }
    /**
     * find by authentication User
     *
     * @author huyqt97
     */
    public User userById(Authentication authentication)throws UserException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (userPrincipal == null || userPrincipal.getUser() == null) {
            throw new UserException("User not found");
        }

        return userPrincipal.getUser();
    }

}
