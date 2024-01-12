package booking_movie.controller;

import booking_movie.dto.request.LoginRequestDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.exception.LoginException;
import booking_movie.exception.RegisterException;
import booking_movie.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws RegisterException {
        return ResponseEntity.ok(userService.register(registerRequestDto));
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws LoginException {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }
}
