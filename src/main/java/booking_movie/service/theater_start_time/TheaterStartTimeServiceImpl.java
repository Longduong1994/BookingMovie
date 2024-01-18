package booking_movie.service.theater_start_time;
import booking_movie.constants.RoomType;
import booking_movie.dto.request.BookingMovieRequest;
import booking_movie.dto.response.TheaterAndStart;
import booking_movie.repository.TheaterStartTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.*;
@Service
public class TheaterStartTimeServiceImpl implements TheaterStartTimeService {
    @Autowired
    TheaterStartTimeRepository theaterStartTimeRepository;
    @Override
    public Map<String, List<LocalTime>> findTheaterAndStartTime(BookingMovieRequest booking, Long idMovie) {
        String roomType = booking.getRoomType();

        List<TheaterAndStart> resultList;

        switch (roomType) {
            case "TWO_D":
                resultList = theaterStartTimeRepository.findTheaterAndStartTime(RoomType.TWO_D, booking.getLocationName(), idMovie, booking.getSelectDate());
                break;

            case "THREE_D":
                resultList = theaterStartTimeRepository.findTheaterAndStartTime(RoomType.THREE_D, booking.getLocationName(), idMovie, booking.getSelectDate());
                break;

            case "FOUR_D":
                resultList = theaterStartTimeRepository.findTheaterAndStartTime(RoomType.FOUR_D, booking.getLocationName(), idMovie, booking.getSelectDate());
                break;

            default:
                return Collections.emptyMap();
        }
        Map<String, List<LocalTime>> theaterToStartTimesMap = new HashMap<>();

        for (TheaterAndStart result : resultList) {
            String theaterName = result.getTheaterName();
            LocalTime startTime = result.getStartTime();

            if (theaterToStartTimesMap.containsKey(theaterName)) {
                theaterToStartTimesMap.get(theaterName).add(startTime);
            } else {
                List<LocalTime> startTimes = new ArrayList<>();
                startTimes.add(startTime);
                theaterToStartTimesMap.put(theaterName, startTimes);
            }
        }

        return theaterToStartTimesMap;
    }

}
