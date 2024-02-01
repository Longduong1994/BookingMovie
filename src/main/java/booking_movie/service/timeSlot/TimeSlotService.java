package booking_movie.service.timeSlot;

import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.dto.request.query.DateTimeAndLocationAndTypeRequest;
import booking_movie.dto.response.TimeSlotResponseDto;
import booking_movie.entity.TimeSlot;
import booking_movie.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {
    Page<TimeSlotResponseDto> findALl(String search, Pageable pageable);
    List<TimeSlotResponseDto> findAllNoSearch();
    TimeSlotResponseDto findById (Long id) throws CustomsException;
    TimeSlotResponseDto save(Authentication authentication , TimeSlotRequestDto timeSlotRequestDto) throws CustomsException;
    TimeSlotResponseDto update(Authentication authentication, Long id,TimeSlotRequestDto timeSlotRequestDto) throws CustomsException;
    void isDelete(Authentication authentication , Long id) throws CustomsException;
    void delete();
    List<TimeSlotResponseDto> findAllByIdMovieAndDateBookingAndIdLocationAndTypeAndIdTheater(Long idMovie , DateTimeAndLocationAndTypeRequest request) throws CustomsException;
}
