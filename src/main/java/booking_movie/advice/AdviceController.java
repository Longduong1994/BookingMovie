package booking_movie.advice;


import booking_movie.exception.*;
import booking_movie.exception.CategoryException;
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

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<String> category(CategoryException categoryException){
        return new ResponseEntity<>(categoryException.getMessage(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginFail(LoginException loginException) {
        return new ResponseEntity<>(loginException.getMessage(), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidRegister(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap();
        ex.getBindingResult().getFieldErrors().forEach(c -> {
            error.put(c.getField(), c.getDefaultMessage());
        });
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LocationException.class)
    public ResponseEntity<String> locationErr(LocationException locationException) {
        return new ResponseEntity<>(locationException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TheaterException.class)
    public ResponseEntity<String> theaterErr(TheaterException theaterException) {
        return new ResponseEntity<>(theaterException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomException.class)
    public ResponseEntity<String> roomErr(RoomException roomException) {
        return new ResponseEntity<>(roomException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChairException.class)
    public ResponseEntity<String> chairErr(ChairException chairException) {
        return new ResponseEntity<>(chairException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenreException.class)
    public ResponseEntity<String> genreErr(GenreException genreException) {
        return new ResponseEntity<>(genreException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieException.class)
    public ResponseEntity<String> movieErr(MovieException movieException) {
        return new ResponseEntity<>(movieException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
