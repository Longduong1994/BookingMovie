package booking_movie.controller;

import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;
import booking_movie.exception.GenreException;
import booking_movie.service.genre.GenreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/genre")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;
    @GetMapping
    public ResponseEntity<Page<GenreResponseDto>> getAllGenre( @RequestParam(defaultValue = "") String search,
                                                              @PageableDefault(size = 2, page = 0, sort = "genreName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<GenreResponseDto> genrePage = genreService.getAllGenre(search, pageable);
        return ResponseEntity.ok(genrePage);
    }
    @PostMapping
    public  ResponseEntity<GenreResponseDto> createGenre(@Valid @RequestBody GenreRequestDto genreRequestDto){
        return new ResponseEntity<>( genreService.createGenre(genreRequestDto), HttpStatus.CREATED);
    }
    @PutMapping("/{idEdit}")
    public ResponseEntity<GenreResponseDto> updateGenre(@Valid @PathVariable Long idEdit,@RequestBody GenreRequestDto genreRequestDto) throws GenreException {
        return new ResponseEntity<>(genreService.updateGenre(genreRequestDto,idEdit), HttpStatus.OK);
    }
    @DeleteMapping("/{idDelete}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long idDelete) throws GenreException {
        genreService.deleteGenre(idDelete);
        return new ResponseEntity<>("xóa thành công",HttpStatus.OK) ;
    }
}
