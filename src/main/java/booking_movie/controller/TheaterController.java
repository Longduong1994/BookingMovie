package booking_movie.controller;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.exception.LocationException;
import booking_movie.exception.TheaterException;
import booking_movie.service.theater.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking/api/v1/theater")
@AllArgsConstructor
public class TheaterController {
    private TheaterService theaterService ;

    @GetMapping()
    public ResponseEntity<?> getAll (@RequestParam(defaultValue = "") String search ,
                                     @PageableDefault(size = 6, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(theaterService.findAll(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws TheaterException {
        return new ResponseEntity<>(theaterService.findById(id), HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TheaterRequestDto theaterRequestDto) throws TheaterException, LocationException {
        return new ResponseEntity<>(theaterService.save(theaterRequestDto), HttpStatus.CREATED) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TheaterRequestDto theaterRequestDto) throws TheaterException, LocationException {
        return new ResponseEntity<>(theaterService.update(id, theaterRequestDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws TheaterException {
        theaterService.delete(id);
        String success = "Theater deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
