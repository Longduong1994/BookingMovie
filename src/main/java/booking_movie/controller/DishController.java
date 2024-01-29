package booking_movie.controller;

import booking_movie.dto.request.dish.DishRequestDto;
import booking_movie.dto.request.dish.DishUpdateRequestDto;
import booking_movie.entity.Dish;
import booking_movie.entity.User;
import booking_movie.exception.DishException;
import booking_movie.exception.UserException;
import booking_movie.service.dish.DishService;
import booking_movie.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllDish(@RequestParam(defaultValue = "") String search,
                                                 @RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "6") Integer size,
                                                 Authentication authentication) throws DishException, UserException {
        return new ResponseEntity<>(dishService.findAll(page,size,search,authentication), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addDish(Authentication authentication,@Valid @ModelAttribute DishRequestDto dishRequestDto) throws DishException, UserException {
        return new ResponseEntity<>(dishService.create(dishRequestDto,authentication),HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable("id") Long id,Authentication authentication) throws DishException, UserException {
        userService.userById(authentication);
        return new ResponseEntity<>(dishService.findById(id),HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Dish> updateDish(@Valid @RequestBody DishUpdateRequestDto dishUpdateRequestDto,Authentication authentication) throws DishException, UserException {
        return new ResponseEntity<>(dishService.update(dishUpdateRequestDto,authentication),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(Authentication authentication, @PathVariable Long id) throws DishException, UserException {
        return new ResponseEntity<>(dishService.delete(id,authentication),HttpStatus.OK);
    }
}
