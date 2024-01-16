package booking_movie.controller;

import booking_movie.exception.LoginException;
import booking_movie.service.user.UserService;
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

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, Authentication authentication) throws LoginException {
        return new ResponseEntity<>(userService.changeStatus(id,authentication),HttpStatus.OK);
    }

}
