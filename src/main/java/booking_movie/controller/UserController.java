package booking_movie.controller;

import booking_movie.dto.request.NewPasswordDto;
import booking_movie.dto.request.PasswordRetrievalDto;
import booking_movie.dto.request.UpdateUserDto;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/booking/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/customers")
    public ResponseEntity<?> findAllCustomer(@RequestParam(defaultValue = "")String username,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "6") int size){
        return new ResponseEntity<>(userService.findAllCustomer(page, size, username), HttpStatus.OK);
    }
    @GetMapping("/manager")
    public ResponseEntity<?> findAllManager(@RequestParam(defaultValue = "")String username,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "6") int size){
        return new ResponseEntity<>(userService.findAllManager(page, size, username), HttpStatus.OK);
    }
    @GetMapping("/employer")
    public ResponseEntity<?> findAllEmployer(@RequestParam(defaultValue = "")String username,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "6") int size){
        return new ResponseEntity<>(userService.findAllEmployer(page, size, username), HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, Authentication authentication) throws LoginException {
        return new ResponseEntity<>(userService.changeStatus(id,authentication),HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, Authentication authentication, @RequestBody @Valid UpdateUserDto updateUserDto) throws CustomsException, LoginException {
        return new ResponseEntity<>(userService.updateCustomer(id, authentication, updateUserDto),HttpStatus.OK);
    }

    @GetMapping("/retrieval")
    public ResponseEntity<?> getRetrieveLink(HttpSession session, @Valid @RequestBody PasswordRetrievalDto passwordRetrievalDto) throws CustomsException {
        return new ResponseEntity<>(userService.getLink(passwordRetrievalDto,session),HttpStatus.OK);
    }

    @PatchMapping("/retrieval")
    public ResponseEntity<?> retrieval(@RequestParam String email, @Valid @RequestBody NewPasswordDto newPasswordDto) throws CustomsException, NotFoundException {
        return new ResponseEntity<>(userService.retrievalPassword(newPasswordDto, email),HttpStatus.OK);
    }

}
