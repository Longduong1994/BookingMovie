package booking_movie.controller;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.service.location.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/booking/v1/location")
@AllArgsConstructor
public class LocationController {
    private LocationService locationService ;

    /*
    * TODO : param ( search / page / limit / sortBy )
    * */
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(name = "search",required = false, defaultValue = "") String search,
                                    @PageableDefault(size = 6, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(locationService.findAll(search, pageable), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllNoSearch() {
        return new ResponseEntity<>(locationService.finAllNoSearch(), HttpStatus.OK);
    }

    /*
     * TODO : param ( idLocation )
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(locationService.findById(id), HttpStatus.OK);
    }

    /*
     * TODO : param ( locationRequest )
     * */
    @PostMapping()
    public ResponseEntity<?> create(@Valid Authentication authentication, @RequestBody LocationRequestDto requestDto) throws CustomsException {

        return new ResponseEntity<>(locationService.save(authentication,requestDto), HttpStatus.CREATED);
    }

    /*
     * TODO : param ( id / locationRequest )
     * */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication, @PathVariable Long id , @RequestBody LocationRequestDto requestDto) throws CustomsException {
        return new ResponseEntity<>(locationService.update(authentication,id, requestDto), HttpStatus.OK) ;
    }

    /*
     * TODO : param ( id )
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication,@PathVariable Long id) throws CustomsException {
        locationService.isDelete(authentication,id) ;
        String success = "Location Deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }


//    @DeleteMapping()
//    public ResponseEntity<?> delete(){
//        locationService.delete();
//        String success = "Location Deleted" ;
//        return new ResponseEntity<>(success, HttpStatus.OK);
//    }
}
