package booking_movie.service.chair;

import booking_movie.dto.request.ChairRequest;
import booking_movie.dto.request.ChairTypeRequest;
import booking_movie.dto.response.ChairResponseDto;
import booking_movie.exception.ChairException;
import booking_movie.exception.RoomException;

import java.util.List;

public interface ChairService {
    List<ChairResponseDto> findAll() ;
    ChairResponseDto findById(Long id) throws ChairException;

    ChairResponseDto changeChairType (Long id , ChairTypeRequest chairTypeRequest) throws ChairException;
    ChairResponseDto update(Long id , ChairRequest chairRequest) throws ChairException, RoomException;
    void delete(Long id) throws ChairException;
}
