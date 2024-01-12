package booking_movie.repository;

import booking_movie.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByRoomNameContainingIgnoreCaseAndIsDeleted(String search, Boolean isDelete, Pageable pageable) ;
    Page<Room> findAllByIsDeleted(Boolean isDelete , Pageable pageable) ;

    Boolean existsByRoomName(String name) ;

}
