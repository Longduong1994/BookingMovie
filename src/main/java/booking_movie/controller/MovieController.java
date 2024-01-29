package booking_movie.controller;

import booking_movie.dto.request.MovieRequestDto;
import booking_movie.dto.response.MovieResponseDto;
import booking_movie.exception.LoginException;
import booking_movie.exception.MovieException;
import booking_movie.service.movie.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/movie")
@AllArgsConstructor
public class MovieController {
    private  final  MovieService movieService;
    @PostMapping
    public ResponseEntity<?> createGenre(@Valid @ModelAttribute MovieRequestDto movieRequestDto, Authentication authentication, BindingResult bindingResult) throws MovieException, LoginException {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        return new ResponseEntity<>( movieService.createMovie(movieRequestDto,authentication), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<MovieResponseDto>> getAllMovie(@RequestParam(defaultValue = "") String search,
                                                              @PageableDefault(size = 6, page = 0, sort = "price", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MovieResponseDto> listMovie = movieService.getAllMovie(search, pageable);
        return ResponseEntity.ok(listMovie);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllNoSearch(){
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/status")
    public ResponseEntity<Page<MovieResponseDto>> getAllMovieByMovieStatus(@RequestParam(defaultValue = "") String search,
                                                                           @RequestParam(defaultValue = "showing") String status,
                                                                           @PageableDefault(size = 2, page = 0, sort = "price", direction = Sort.Direction.ASC) Pageable pageable) throws MovieException {
        Page<MovieResponseDto> listMovie = movieService.getAllMovieByMovieStatus(search, pageable,status);
        return ResponseEntity.ok(listMovie);
    }
    @DeleteMapping("/{idDelete}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long idDelete) throws MovieException {
        movieService.deleteMovie(idDelete);
        return new ResponseEntity<>("xóa thành công",HttpStatus.OK) ;
    }
    @GetMapping("/{idMovie}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long idMovie) throws MovieException {

        return new ResponseEntity<>(movieService.getMovieById(idMovie),HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@Valid @PathVariable Long id, @ModelAttribute MovieRequestDto movieRequestDto,Authentication authentication, BindingResult bindingResult) throws MovieException, LoginException {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        return new ResponseEntity<>(movieService.updateMovie(movieRequestDto,authentication,id), HttpStatus.OK);
    }

    private ResponseEntity<String> handleValidationErrors(BindingResult bindingResult) {
        StringBuilder errorMessages = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessages.append(error.getDefaultMessage()).append("\n");
        }
        return new ResponseEntity<>(errorMessages.toString(), HttpStatus.BAD_REQUEST);
    }
}
