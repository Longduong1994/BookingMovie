package booking_movie.repository;

import booking_movie.entity.Notification;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("SELECT N FROM Notification N JOIN N.users U WHERE U.id = :userId")
    Page<Notification> findByUserId(@Param("userId") Long userId, Pageable pageable);


    void deleteByReadAndUsers(@Param(("read")) boolean read,@Param(("users")) User users);
    void deleteByIdIn(Set<Long> ids);

}
