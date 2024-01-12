package booking_movie.service.location;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.exception.LocationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface LocationService {
    Page<LocationResponseDto> findAll(String locationName , Pageable pageable) ;
    LocationResponseDto findById (Long id) throws LocationException;

    LocationResponseDto save (LocationRequestDto locationRequestDto) throws LocationException;

    LocationResponseDto update(Long id , LocationRequestDto locationRequestDto) throws LocationException;

    void delete (Long id) throws LocationException;
}
