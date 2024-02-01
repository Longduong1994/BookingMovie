package booking_movie.service.theater_start_time;
import booking_movie.constants.ChairType;
import booking_movie.constants.RoomType;
import booking_movie.dto.request.BookingMovieRequest;
import booking_movie.dto.response.ChairByRoomDto;
import booking_movie.dto.response.TheaterAndStart;
import booking_movie.entity.Location;
import booking_movie.entity.Room;
import booking_movie.exception.NotFoundException;
import booking_movie.repository.TheaterStartTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TheaterStartTimeServiceImpl implements TheaterStartTimeService {
    @Autowired
    TheaterStartTimeRepository theaterStartTimeRepository;

    @Override
    public List<Location> findLocationMovie(Long idMovie, LocalDate selectDate, String roomType) {
        List<Location> resultList;

        switch (roomType) {
            case "TWO_D":
                resultList = theaterStartTimeRepository.findDistinctLocationsByRoomTypeAndMovieIdAndShowDate(RoomType.TWO_D,  idMovie, selectDate);
                break;

            case "THREE_D":
                resultList = theaterStartTimeRepository.findDistinctLocationsByRoomTypeAndMovieIdAndShowDate(RoomType.THREE_D,  idMovie, selectDate);
                break;

            case "FOUR_D":
                resultList = theaterStartTimeRepository.findDistinctLocationsByRoomTypeAndMovieIdAndShowDate(RoomType.FOUR_D,  idMovie, selectDate);
                break;

            default:
                return null;
        }

        return resultList;
    }

    @Override
    public Map<String, List<LocalTime>> findTheaterAndStartTime(Long idMovie, LocalDate selectDate, String roomType, String locationName) {

        List<TheaterAndStart> resultList;

        switch (roomType) {
            case "TWO_D":
                resultList = theaterStartTimeRepository.findTheaterAndStartTime(RoomType.TWO_D, locationName, idMovie, selectDate);
                break;

            case "THREE_D":
                resultList = theaterStartTimeRepository.findTheaterAndStartTime(RoomType.THREE_D,locationName , idMovie, selectDate);
                break;

            case "FOUR_D":
                resultList = theaterStartTimeRepository.findTheaterAndStartTime(RoomType.THREE_D, locationName, idMovie, selectDate);
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

    @Override
    public List<Room> getRoomByMovieId(Long idMovie, LocalDate selectDate, String roomType, LocalTime startTime, String theaterName) {
        List<Room> room;
        switch (roomType) {
            case "TWO_D":
                room = theaterStartTimeRepository.findRoomsByTheaterNameAndShowDateAndStartTimeAndMovieId(theaterName, selectDate,startTime,idMovie, RoomType.TWO_D );
                break;

            case "THREE_D":
                room = theaterStartTimeRepository.findRoomsByTheaterNameAndShowDateAndStartTimeAndMovieId(theaterName, selectDate,startTime, idMovie,RoomType.THREE_D );
                break;

            case "FOUR_D":
                room = theaterStartTimeRepository.findRoomsByTheaterNameAndShowDateAndStartTimeAndMovieId(theaterName, selectDate,startTime,idMovie,RoomType.FOUR_D );
                break;

            default:
                return null;
        }

        return room;
    }

    @Override
    public List<ChairByRoomDto> getChairByRoom(Long idRoom, LocalTime startTime) {
        List<Object[]> results = theaterStartTimeRepository.getChairsData(startTime, idRoom);
        return results.stream()
                .map(result -> new ChairByRoomDto(
                        (Long) result[0],   // id
                        (String) result[1], // chairName
                        ChairType.valueOf((String) result[2]), // chairType
                        (String) result[4], // status
                        (Boolean) result[3] // isDeleted
                ))
                .collect(Collectors.toList());
    }


}
