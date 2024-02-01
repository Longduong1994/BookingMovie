package booking_movie.mapper;

import booking_movie.dto.request.TimeSlotRequestDto;
import booking_movie.dto.response.TimeSlotResponseDto;
import booking_movie.entity.Movie;
import booking_movie.entity.Room;
import booking_movie.entity.Theater;
import booking_movie.entity.TimeSlot;
import booking_movie.exception.CustomsException;
import booking_movie.repository.MovieRepository;
import booking_movie.repository.RoomRepository;
import booking_movie.repository.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimeSlotMapper {
    private MovieRepository movieRepository ;
    private RoomRepository roomRepository ;
    private TheaterRepository theaterRepository;

    public TimeSlotResponseDto toResponse (TimeSlot timeSlot) throws CustomsException {
        String typeTime = switch (timeSlot.getRoom().getRoomType()) {
            case TWO_D -> "2D" ;
            case THREE_D -> "3D";
            case FOUR_D -> "4D";
            default -> throw new CustomsException("Kiểu Phòng Không tồn tại") ;
        };
        return TimeSlotResponseDto.builder()
                .id(timeSlot.getId())
                .movieName(timeSlot.getMovie().getMovieName())
                .movieId(timeSlot.getMovie().getId())
                .roomName(timeSlot.getRoom().getRoomName())
                .roomId(timeSlot.getRoom().getId())
                .theaterName(timeSlot.getRoom().getTheater().getTheaterName())
                .theaterId(timeSlot.getRoom().getTheater().getId())
                .startTime(timeSlot.getStartTime())
                .showDateMovie(timeSlot.getShowDateMovie())
                .roomType(typeTime)
                .build();
    }

    public TimeSlot toEntity(TimeSlotRequestDto timeSlotRequestDto) throws CustomsException {
        Movie movie = movieRepository.findById(timeSlotRequestDto.getMovieId()).orElseThrow(() -> new CustomsException("Phim không tồn tại"));
        Room room = roomRepository.findById(timeSlotRequestDto.getRoomId()).orElseThrow(() -> new CustomsException("Phòng chiếu không tồn tại"));
        Theater theater = theaterRepository.findById(timeSlotRequestDto.getTheaterId()).orElseThrow(() -> new CustomsException("Rạp chiếu không tồn tại"));
        return TimeSlot.builder()
                .movie(movie)
                .room(room)
                .showDateMovie(timeSlotRequestDto.getShowDateMovie())
                .startTime(timeSlotRequestDto.getStartTime())
                .isDeleted(timeSlotRequestDto.getIsDeleted())
                .build();
    }
}
