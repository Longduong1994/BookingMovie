package booking_movie.controller;

import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.PasswordRetrievalDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.exception.RegisterException;
import booking_movie.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/auth")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;


    @PostMapping("/login")
    private ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws LoginException {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws RegisterException {

        return new ResponseEntity<>(userService.register(registerRequestDto), HttpStatus.OK);
    }


    @PostMapping("/createAccount")
    private ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccountDto createAccountDto, Authentication authentication) throws LoginException, NotFoundException, RegisterException {
        return ResponseEntity.ok(userService.createAccount(createAccountDto,authentication));
    }

    @PostMapping("getLink")
    public ResponseEntity<?> getLink(@Valid @RequestBody PasswordRetrievalDto passwordRetrievalDto) throws CustomsException {
        return new ResponseEntity<>(userService.getLink(passwordRetrievalDto),HttpStatus.OK);
    }


}