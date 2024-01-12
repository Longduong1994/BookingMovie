package booking_movie.controller;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.exception.LocationException;
import booking_movie.service.location.LocationService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking/api/v1/location")
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

    /*
     * TODO : param ( idLocation )
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws LocationException {
        return new ResponseEntity<>(locationService.findById(id), HttpStatus.OK);
    }

    /*
     * TODO : param ( locationRequest )
     * */
    @PostMapping()
    public ResponseEntity<?> create(LocationRequestDto requestDto) throws LocationException {
        return new ResponseEntity<>(locationService.save(requestDto), HttpStatus.CREATED);
    }

    /*
     * TODO : param ( id / locationRequest )
     * */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id , @RequestBody LocationRequestDto requestDto) throws LocationException {
        return new ResponseEntity<>(locationService.update(id, requestDto), HttpStatus.OK) ;
    }

    /*
     * TODO : param ( id )
     * */
    @PatchMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws LocationException {
        locationService.delete(id) ;
        String success = "Location Deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }





}
