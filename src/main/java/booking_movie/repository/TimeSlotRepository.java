package booking_movie.repository;

import booking_movie.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot , Long> {
    List<TimeSlot> findAllByIsDeletedFalse();
    List<TimeSlot> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime);
    List<TimeSlot> findAllByMovieIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    List<TimeSlot> findAllByRoomIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
}
