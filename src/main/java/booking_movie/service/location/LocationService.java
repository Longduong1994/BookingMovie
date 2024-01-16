package booking_movie.service.location;

import booking_movie.dto.request.LocationRequestDto;
import booking_movie.dto.response.LocationResponseDto;
import booking_movie.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


public interface LocationService {
    Page<LocationResponseDto> findAll(String locationName , Pageable pageable) ;
    LocationResponseDto findById (Long id) throws  CustomsException;

    LocationResponseDto save (Authentication authentication, LocationRequestDto locationRequestDto) throws CustomsException;

    LocationResponseDto update(Authentication authentication,Long id , LocationRequestDto locationRequestDto) throws CustomsException;

    void isDelete (Authentication authentication, Long id) throws CustomsException;
    void delete () ;

}
