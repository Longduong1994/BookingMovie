package booking_movie.service.room;

import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.request.RoomUpdateRequestDto;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeAndTimeSlotRequest;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RoomService {
    Page<RoomResponseDto> findAll(String search, Pageable pageable) ;
    List<RoomResponseDto> finAllNoSearch();
    List<RoomResponseDto> findAllByTheaterId(Long idTheater) throws CustomsException;

    RoomResponseDto findById(Long id) throws CustomsException;

    RoomResponseDto save (Authentication authentication,  RoomRequestDto roomRequestDto) throws  CustomsException;

    RoomResponseDto update (Authentication authentication,Long id , RoomUpdateRequestDto roomRequestDto) throws CustomsException;
    void isDelete (Authentication authentication,Long id) throws CustomsException;
    void delete();
    RoomResponseDto findByMovieAndDateBookingAndLocationAndTypeAndTimeSlot(Long idMovie , DateTimeAndLocationAndTypeAndTimeSlotRequest request) throws CustomsException;
}
