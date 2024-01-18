package booking_movie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking/v1/staff")
public class StaffController {
    @RequestMapping("/")
    public ResponseEntity<?> findAll(){
        return new  ResponseEntity<>("dass", HttpStatus.OK);
    };
}
