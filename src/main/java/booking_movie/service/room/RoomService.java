package booking_movie.service.room;

import booking_movie.dto.request.RoomRequestDto;
import booking_movie.dto.response.RoomResponseDto;
import booking_movie.exception.RoomException;
import booking_movie.exception.TheaterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    Page<RoomResponseDto> findAll(String search, Pageable pageable) ;

    RoomResponseDto findById(Long id) throws RoomException;

    RoomResponseDto save (RoomRequestDto roomRequestDto) throws RoomException, TheaterException;

    RoomResponseDto update (Long id , RoomRequestDto roomRequestDto) ;
    void delete (Long id) ;
}
