package booking_movie.controller;

import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.RegisterException;
import booking_movie.service.user.UserService;
import booking_movie.service.verification.VerificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private  UserService userService;
    @Autowired
    private  VerificationService verificationService;

    @GetMapping("/captcha")
    public String generateRandomCaptcha(HttpSession session) {
        String captcha = verificationService.generateRandomCaptcha();
        session.setAttribute("captchaCode", captcha);
        return captcha;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto,HttpSession session) throws RegisterException {
        return ResponseEntity.ok(userService.register(registerRequestDto,session));
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto,HttpSession session) throws LoginException {
        return ResponseEntity.ok(userService.login(loginRequestDto,session));
    }

    @PostMapping("/createAccount")
    private ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccountDto createAccountDto, Authentication authentication) throws LoginException, NotFoundException, RegisterException {
        return ResponseEntity.ok(userService.createAccount(createAccountDto,authentication));
    }

}
