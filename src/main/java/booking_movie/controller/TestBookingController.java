package booking_movie.controller;

import booking_movie.dto.request.query.DateTimeAndLocationAndTypeAndTimeSlotRequest;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeRequest;
import booking_movie.exception.CustomsException;
import booking_movie.repository.RoomRepository;
import booking_movie.service.chair.ChairService;
import booking_movie.service.room.RoomService;
import booking_movie.service.theater.TheaterService;
import booking_movie.service.timeSlot.TimeSlotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestBookingController {
    private TimeSlotService timeSlotService;
    private TheaterService theaterService ;
    private RoomService roomService;
    private ChairService chairService ;
    @GetMapping("/getTimeSlot")
    public ResponseEntity<?> getTimeSlot(@Valid @RequestParam(name = "idMovie",required = false, defaultValue = "") Long idMovie ,
                                                   @RequestBody DateTimeAndLocationAndTypeRequest request) throws CustomsException {

        return new ResponseEntity<>(timeSlotService.findAllByIdMovieAndDateBookingAndIdLocationAndTypeAndIdTheater(idMovie,request), HttpStatus.OK);
    }

    @GetMapping("/getTheater")
    public ResponseEntity<?> getTheater(@Valid @RequestParam(name = "idMovie", required = false, defaultValue = "")Long idMovie,
                                        @RequestBody DateTimeAndLocationAndTypeRequest request) throws CustomsException {
        return new ResponseEntity<>(theaterService.findByMovieAndDateBookingAndLocationAndType(idMovie,request), HttpStatus.OK);
    }

    @GetMapping("/getRoom")
    public ResponseEntity<?> getRoom(@Valid @RequestParam(name = "idMovie", required = false, defaultValue = "") Long idMovie,
                                     @RequestBody DateTimeAndLocationAndTypeAndTimeSlotRequest request) throws CustomsException {
        return new ResponseEntity<>(roomService.findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(idMovie,request), HttpStatus.OK);
    }

    @GetMapping("/getChair")
    public ResponseEntity<?> getChair(@Valid @RequestParam(name = "idMovie", required = false, defaultValue = "")Long idMovie,
                                      @RequestBody DateTimeAndLocationAndTypeAndTimeSlotRequest request) throws CustomsException {
        return new ResponseEntity<>(chairService.findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(idMovie,request), HttpStatus.OK);
    }

}
