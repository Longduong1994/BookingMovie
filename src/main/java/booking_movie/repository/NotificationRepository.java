package booking_movie.repository;

import booking_movie.entity.Notification;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query(value = "SELECT n.* FROM notification n JOIN notification_user nu ON n.id = nu.notification_id JOIN users u ON u.id = nu.user_id WHERE u.id = :userId ORDER BY n.id DESC", nativeQuery = true)
    List<Notification> findByUserId(@Param("userId") Long userId);



    void deleteByReadAndUsers(@Param(("read")) boolean read,@Param(("users")) User users);
    void deleteByIdIn(Set<Long> ids);

}
