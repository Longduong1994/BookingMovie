package booking_movie.controller;

import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.service.timeSlot.TimeSlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking/v1/timeSlot")
public class TimeSlotController {
    private TimeSlotService timeSlotService ;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(timeSlotService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(timeSlotService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create (@Valid Authentication authentication, @RequestBody TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
        return  new ResponseEntity<>(timeSlotService.save(authentication,timeSlotRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication , @PathVariable Long id , @RequestBody TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
        return new ResponseEntity<>(timeSlotService.update(authentication, id, timeSlotRequestDto), HttpStatus.OK);
    }

//    @DeleteMapping()
//    public ResponseEntity<?> delete() {
//        timeSlotService.delete();
//        String success = "Theater deleted" ;
//        return new ResponseEntity<>(success, HttpStatus.OK);
//    }
}
