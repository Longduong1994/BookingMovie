package booking_movie.controller;

import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.request.RoomUpdateRequestDto;
import booking_movie.exception.CustomsException;
import booking_movie.service.room.RoomService;
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
@RequestMapping("/api/booking/v1/room")
@AllArgsConstructor
public class RoomController {
    private RoomService roomService ;
    @GetMapping()

    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String search ,
                                    @PageableDefault(size = 6, page = 0, sort = "id" , direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(roomService.findAll(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/byIdTheater/{id}")
    public ResponseEntity<?> getAllByTheaterId(@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(roomService.findAllByTheaterId(id), HttpStatus.OK);
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllNoSearch() {
        return new ResponseEntity<>(roomService.finAllNoSearch(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid Authentication authentication, @RequestBody RoomRequestDto roomRequestDto) throws CustomsException {
        return new ResponseEntity<>(roomService.save(authentication,roomRequestDto),HttpStatus.CREATED);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication,@PathVariable Long id, @RequestBody RoomUpdateRequestDto roomUpdateRequestDto) throws CustomsException {
        return new ResponseEntity<>(roomService.update(authentication,id,roomUpdateRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication,@PathVariable Long id) throws CustomsException {
        roomService.isDelete(authentication,id);
        String success = "Room Deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

//    @DeleteMapping()
//    public ResponseEntity<?> delete(){
//        roomService.delete();
//        String success = "Room Deleted" ;
//        return new ResponseEntity<>(success, HttpStatus.OK);
//    }
}
