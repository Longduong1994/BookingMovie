package booking_movie.service.timeSlot;

import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.dto.response.TimeSlotResponseDto;
import booking_movie.exception.CustomsException;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TimeSlotService {
    List<TimeSlotResponseDto> findAll();
    TimeSlotResponseDto findById (Long id) throws CustomsException;
    TimeSlotResponseDto save(Authentication authentication , TimeSlotRequestDto timeSlotRequestDto) throws CustomsException;
    TimeSlotResponseDto update(Authentication authentication, Long id,TimeSlotRequestDto timeSlotRequestDto) throws CustomsException;
    void isDelete(Authentication authentication , Long id) throws CustomsException;
    void delete();
}
