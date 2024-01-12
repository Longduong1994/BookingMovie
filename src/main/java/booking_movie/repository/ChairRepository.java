package booking_movie.repository;

import booking_movie.entity.Chair;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChairRepository extends JpaRepository<Chair, Long> {

    List<Chair> findAllByIsDeleted(Boolean isDeleted) ;

    Boolean existsByChairNameAndRoomId(String name , Long idRoom) ;
}
