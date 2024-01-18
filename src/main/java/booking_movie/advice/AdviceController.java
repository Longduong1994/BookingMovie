package booking_movie.advice;


import booking_movie.exception.*;
import booking_movie.exception.LoginException;
import booking_movie.exception.RegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<String> registerFail(RegisterException registerException) {
        return new ResponseEntity<>(registerException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginFail(LoginException loginException) {
        return new ResponseEntity<>(loginException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidRegister(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(c -> {
            error.put(c.getField(), c.getDefaultMessage());
        });
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomsException.class)
    public ResponseEntity<String> customException(CustomsException customsException) {
        return new ResponseEntity<>(customsException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    /**
     * exception category
     *
     * @author huyqt97
     */
    @ExceptionHandler(DishException.class)
    public ResponseEntity<String> DishErr(DishException dishException){
        return new ResponseEntity<>(dishException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    /**
     * exception category
     *
     * @author huyqt97
     */
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<String> categoryErr(CategoryException categoryException){
        return new ResponseEntity<>(categoryException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    /**
     * exception promtion
     *
     * @author huyqt97
     */
    @ExceptionHandler(PromtionException.class)
    public ResponseEntity<String> promtion(PromtionException promtionException) {
        return new ResponseEntity<>(promtionException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * exception promtion
     *
     * @author huyqt97
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> user(UserException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     *exception order
     *
     * @author huyqt97
     */
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<String> order(OrderException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     *exception Menu
     *
     * @author huyqt97
     */
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<String> menu(MenuException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenreException.class)
    public ResponseEntity<String> genreErr(GenreException genreException) {
        return new ResponseEntity<>(genreException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieException.class)
    public ResponseEntity<String> movieErr(MovieException movieException) {
        return new ResponseEntity<>(movieException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFound(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
