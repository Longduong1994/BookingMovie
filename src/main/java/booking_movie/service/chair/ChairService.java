package booking_movie.service.chair;

import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.request.ChairTypeRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.exception.CustomsException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChairService {
    List<ChairResponseDto> findAll() ;
    ChairResponseDto findById(Long id) throws CustomsException;

    ChairResponseDto changeChairType (Authentication authentication, Long id , ChairTypeRequest chairTypeRequest) throws CustomsException;
    ChairResponseDto update(Authentication authentication,Long id , ChairRequest chairRequest) throws CustomsException;
    void isDelete(Authentication authentication,Long id) throws CustomsException;
    void delete();
}
