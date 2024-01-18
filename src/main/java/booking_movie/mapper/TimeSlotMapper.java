package booking_movie.mapper;

import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.dto.response.TimeSlotResponseDto;
import booking_movie.entity.Movie;
import booking_movie.entity.Room;
import booking_movie.entity.TimeSlot;
import booking_movie.exception.CustomsException;
import booking_movie.repository.MovieRepository;
import booking_movie.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimeSlotMapper {
    private MovieRepository movieRepository ;
    private RoomRepository roomRepository ;

    public TimeSlotResponseDto toResponse (TimeSlot timeSlot) {
        return TimeSlotResponseDto.builder()
                .id(timeSlot.getId())
                .movieName(timeSlot.getMovie().getMovieName())
                .roomName(timeSlot.getRoom().getRoomName())
                .startTime(timeSlot.getStartTime())
                .build();
    }

    public TimeSlot toEntity(TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
        Movie movie = movieRepository.findById(timeSlotRequestDto.getMovieId()).orElseThrow(() -> new CustomsException("Movie Not Found"));
        Room room = roomRepository.findById(timeSlotRequestDto.getRoomId()).orElseThrow(() -> new CustomsException("Room Not Found"));
        return TimeSlot.builder()
                .movie(movie)
                .room(room)
                .startTime(timeSlotRequestDto.getStartTime())
                .isDeleted(timeSlotRequestDto.getIsDeleted())
                .build();
    }
}
