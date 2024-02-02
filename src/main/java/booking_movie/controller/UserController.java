package booking_movie.controller;

import booking_movie.dto.request.*;
import booking_movie.dto.response.CustomerResponse;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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


    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication) throws LoginException {
        return new ResponseEntity<>(userService.profile(authentication),HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changPassword(Authentication authentication, @Valid @RequestBody ChangePasswordDto changePasswordDto) throws LoginException, CustomsException {
        return new ResponseEntity<>(userService.changePassword(changePasswordDto,authentication),HttpStatus.OK);
    }

    @PostMapping("/changeAvatar")
    public ResponseEntity<?> changeAvatar(Authentication authentication,@Valid @ModelAttribute AvatarUploadDto avatarUploadDto) throws LoginException {
        return new ResponseEntity<>(userService.uploadAvatar(authentication,avatarUploadDto),HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> findAllCustomer(@RequestParam(defaultValue = "")String username,
                                             @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(userService.findAllCustomer(page,username), HttpStatus.OK);
    }
    @GetMapping("/manager")
    public ResponseEntity<?> findAllManager(@RequestParam(defaultValue = "")String username,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size){
        return new ResponseEntity<>(userService.findAllManager(page, size, username), HttpStatus.OK);
    }
    @GetMapping("/employer")
    public ResponseEntity<?> findAllEmployer(@RequestParam(defaultValue = "")String username,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "2") int size){
        return new ResponseEntity<>(userService.findAllEmployer(page, size, username), HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, Authentication authentication) throws LoginException {
        return new ResponseEntity<>(userService.changeStatus(id,authentication),HttpStatus.OK);
    }

    @PatchMapping("/updateProfile")
    public ResponseEntity<?> update( Authentication authentication, @RequestBody @Valid UpdateUserDto updateUserDto) throws CustomsException, LoginException {
        return new ResponseEntity<>(userService.updateCustomer(authentication, updateUserDto),HttpStatus.OK);
    }


    @PutMapping("/retrieval")
    public ResponseEntity<?> retrieval(@RequestParam String email, @Valid @RequestBody NewPasswordDto newPasswordDto) throws CustomsException, NotFoundException {
        return new ResponseEntity<>(userService.retrievalPassword(newPasswordDto, email),HttpStatus.OK);
    }

    @PatchMapping("/setRole/{id}")
    public ResponseEntity<?> setRole(@PathVariable Long id,Authentication authentication,@RequestParam String roleName) throws CustomsException, LoginException, NotFoundException {

        return new ResponseEntity<>(userService.setRole(authentication,roleName,id),HttpStatus.OK);
    }
// tổng số người dùng
    @GetMapping("/count-customer")
    private ResponseEntity<?> sumCustomer(){
        return new ResponseEntity<>(userService.sumCustomer(),HttpStatus.OK);
    }
}
