package booking_movie.controller;
import booking_movie.dto.request.BookingMovieRequest;
import booking_movie.service.theater_start_time.TheaterStartTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking/v1/view")

public class TheaterStartTimeController {
    @Autowired
    private TheaterStartTimeService theaterStartTimeService;
    @GetMapping("/{idMovie}")
  public ResponseEntity<  Map<String, List<LocalTime>>> findTheater(@PathVariable Long idMovie, @RequestBody BookingMovieRequest bookingMovieRequest){
        Map<String, List<LocalTime>> booKingMovieResponses=  theaterStartTimeService.findTheaterAndStartTime(bookingMovieRequest, idMovie);
        return ResponseEntity.ok(booKingMovieResponses);
    }

}
