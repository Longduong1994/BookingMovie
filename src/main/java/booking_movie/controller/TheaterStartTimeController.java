package booking_movie.controller;

import booking_movie.dto.request.BookingMovieRequest;
import booking_movie.service.theater_start_time.TheaterStartTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking/v1/view")

public class TheaterStartTimeController {
    @Autowired
    private TheaterStartTimeService theaterStartTimeService;

    @GetMapping("/{idMovie}")
    public ResponseEntity<Map<String, List<LocalTime>>> findTheater(@PathVariable String idMovie,
                                                                    @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate selectDate,
                                                                    @RequestParam String roomType,
                                                                    @RequestParam String locationName) {

        Long id = Long.parseLong(idMovie);
        Map<String, List<LocalTime>> booKingMovieResponses = theaterStartTimeService.findTheaterAndStartTime(id, selectDate, roomType, locationName);
        return ResponseEntity.ok(booKingMovieResponses);
    }

    @GetMapping("/locationOfMovie/{idMovie}")
    public ResponseEntity<?> getLocationOfMovie(@PathVariable String idMovie,
                                                @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate selectDate,
                                                @RequestParam String roomType ){
        Long id = Long.parseLong(idMovie);
        return new ResponseEntity<>(theaterStartTimeService.findLocationMovie(id, selectDate, roomType), HttpStatus.OK);
    }

    @GetMapping("/room")
    public ResponseEntity<?> getRoom(@RequestParam String idMovie,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate selectDate,
                                     @RequestParam String roomType ,
                                     @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
                                     @RequestParam String theaterName){
        Long id = Long.parseLong(idMovie);
        return new  ResponseEntity<>(theaterStartTimeService.getRoomByMovieId(id,selectDate,roomType,startTime,theaterName),HttpStatus.OK);
    }

    @GetMapping("/chair")
    private ResponseEntity<?> getChairByRoom(@RequestParam String idRoom,
                                             @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime
                                             ){
        Long id = Long.parseLong(idRoom);
        return new ResponseEntity<>(theaterStartTimeService.getChairByRoom(id, startTime),HttpStatus.OK);
    }

}
