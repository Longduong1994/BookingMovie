package booking_movie.controller;

import booking_movie.dto.request.TheaterRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.service.theater.TheaterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/theater")
@AllArgsConstructor
public class TheaterController {
    private TheaterService theaterService ;

    @GetMapping()
    public ResponseEntity<?> getAll (@RequestParam(defaultValue = "") String search ,
                                     @PageableDefault(size = 6, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(theaterService.findAll(search,pageable), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getALlNoSearch(){
        return new ResponseEntity<>(theaterService.finAllNoSearch(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(theaterService.findById(id), HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid Authentication authentication,  @RequestBody TheaterRequestDto theaterRequestDto) throws CustomsException {
        return new ResponseEntity<>(theaterService.save(authentication,theaterRequestDto), HttpStatus.CREATED) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication, @PathVariable Long id, @RequestBody TheaterRequestDto theaterRequestDto) throws  CustomsException {
        return new ResponseEntity<>(theaterService.update(authentication,id, theaterRequestDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication,@PathVariable Long id) throws CustomsException {
        theaterService.isDelete(authentication,id);
        String success = "Theater deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

//    @DeleteMapping()
//    public ResponseEntity<?> delete() {
//        theaterService.delete();
//        String success = "Theater deleted" ;
//        return new ResponseEntity<>(success, HttpStatus.OK);
//    }
}
