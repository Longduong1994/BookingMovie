package booking_movie.service.user;



import java.time.Instant;
import java.util.Base64;
import booking_movie.dto.request.*;
import booking_movie.dto.response.*;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.RegisterException;
import booking_movie.exception.UserException;
import booking_movie.mapper.UsersMapper;
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


import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UsersMapper userMapper;
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
            throw new RuntimeException("Bạn không có quyền.");
        }
        Optional<User> userChange = userRepository.findById(id);
        if (!userChange.isPresent()){
            throw new RuntimeException("Không tìm thấy tài khoản.");
        }
        userChange.get().setStatus(!userChange.get().getStatus());
        userRepository.save(userChange.get());
        return "Thay đổi trạng thái thành công.";
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
        String password =createAccountDto.getUsername() + UUID.randomUUID().toString().substring(0, 5);


        User newUser = userMapper.toEntity(createAccountDto);
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(password));
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
    public String getLink(PasswordRetrievalDto passwordRetrievalDto) throws CustomsException {
        Optional<User> user = userRepository.findByEmail(passwordRetrievalDto.getEmail());
        if (!user.isPresent()) {
            throw new CustomsException("Địa chỉ email không đúng");
        }

        String originalInput = passwordRetrievalDto.getEmail();
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        String verification = verificationService.create(user.get()).getVerificationCode();

        String link = "http://localhost:3000/retrievalpassword?email=" + encodedString;

        String emailContent = String.format("""
<div style="text-align: center; font-family: Arial, sans-serif; color: #333;">
    <p>
        Cinima.vn đã nhận được yêu cầu thay đổi mật khẩu của quý khách.
    </p>
    <p>Mã xác nhận: <span style="font-weight: bold;">%s</span></p>
    <p>
        Xin hãy click vào link "ĐỔI MẬT KHẨU" bên dưới để đổi mật khẩu: 
    </p>
    <a href="%s" style="text-decoration: none;">
        <button style="background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border-radius: 5px;">
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
""", verification, link);

        mailService.sendMail(passwordRetrievalDto.getEmail(), "Yêu cầu thay đổi mật khẩu", emailContent);
        return "Bạn hãy đăng nhập email: "+ passwordRetrievalDto.getEmail()+" và làm theo hướng dẫn.";
    }



    @Override
    public String changePassword(ChangePasswordDto changePasswordDto, Authentication authentication) throws LoginException, CustomsException {
        User user = getUser(authentication);
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new CustomsException("Old password does not match");
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())){
            throw new CustomsException("Confirm new password does not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return "Đổi mật khẩu thành công";
    }

    @Override
    public String retrievalPassword(NewPasswordDto newPasswordDto,String email) throws NotFoundException, CustomsException {
        String encodedString = email;

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);


        Optional<User> user = userRepository.findByEmail(decodedString);
        if(!user.isPresent()){
            throw new CustomsException("Không tìm thấy người dùng");
        }else if (!verificationService.isExpired(newPasswordDto.getVerification())){
            throw new CustomsException("Mã xác nhận hết hạn .");
        }else if(!verificationService.isVerification(newPasswordDto.getVerification(),user.get())){
            throw new CustomsException("Mã xác nhận không đúng.");
        }
        else if(!newPasswordDto.getPassword().equals(newPasswordDto.getConfirmPassword())){
            throw new CustomsException("Xác nhận mật khẩu mới không đúng.");
        }
        else if(!newPasswordDto.getPassword().equals(newPasswordDto.getConfirmPassword())){
            throw new CustomsException("Xác nhận mật khẩu mới không đúng.");
        } else {
            user.get().setPassword(passwordEncoder.encode(newPasswordDto.getPassword()));
            userRepository.save(user.get());
            verificationService.resetVerification(newPasswordDto.getVerification());
        }
        return "Đặt lại mật khẩu thành công!";
    }

    @Override
    public CustomerResponse updateCustomer(Authentication authentication, UpdateUserDto updateUserDto) throws CustomsException, LoginException {
        User user = getUser(authentication);
        user.setAddress(updateUserDto.getAddress());
        user.setCity(updateUserDto.getCity());
        user.setPhone(updateUserDto.getPhone());
        user.setGender(updateUserDto.getGender());
        userRepository.save(user);
        return userMapper.toCustomer(user);
    }


    @Override
    public UserProfileDto uploadAvatar(Authentication authentication, AvatarUploadDto avatarUploadDto) throws LoginException {
        User user = getUser(authentication);
        user.setAvatar(uploadFileService.uploadFile(avatarUploadDto.getFile()));
        userRepository.save(user);
        return userMapper.toProfile(user);
    }

    @Override
    public String setRole(Authentication authentication, String roleName, Long id) throws LoginException, CustomsException, NotFoundException {
        User user = getUser(authentication);
        if (!roleService.hasRoleAdmin(user)){
            throw new CustomsException("You have no rights ");
        }
        Optional<User> staff = userRepository.findById(id);
        if (!staff.isPresent()) {
            throw new NotFoundException("User " + id + "not found");
        }
        Role role = roleService.findByRoleName(roleName);
        Set<Role> setRole= new HashSet<>();
        setRole.add(role);
        staff.get().setRoles(setRole);
        userRepository.save(staff.get());
        return "Success";
    }

    @Override
    public Page<CustomerResponse> findAllCustomer(int page,String username) {

        int size =6;

        Page<User> customers = userRepository.findAllCustomers(username,roleService.getRoleCustomer(),PageRequest.of(page,size));
        return customers.map(userMapper::toCustomer);
    }

    @Override
    public Page<ManagerResponse> findAllManager(int page, int size, String username) {
        Page<User> customers = userRepository.findAllCustomers(username,roleService.getRoleManager(),PageRequest.of(page,size));
        return customers.map(userMapper::toManager);
    }

    @Override
    public UserProfileDto profile( Authentication authentication) throws LoginException {
        User user = getUser(authentication);
        return userMapper.toProfile(user);
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
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRoles(roles);
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
            throw new LoginException("Tài khoản hoặc mật khẩu không đúng.");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUser().getStatus()) {
            throw new LoginException("Tài khoản đã bị khóa.");
        }
        String token = jwtProvider.generateToken(userPrincipal);
        return UserResponseDto.builder()
                .username(userPrincipal.getUser().getUsername())
                .token(token)
                .setRoles(userPrincipal.getUser().getRoles().stream().map(item -> item.getRoleName().name()).collect(Collectors.toSet()))
                .build();
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
