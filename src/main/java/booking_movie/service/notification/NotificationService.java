package booking_movie.service.notification;

import booking_movie.dto.request.DeleteNotification;
import booking_movie.dto.response.NotificationResponse;
import booking_movie.entity.Notification;
import booking_movie.exception.LoginException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface NotificationService {
    Page<NotificationResponse> getNotification(Authentication authentication, int page) throws LoginException;

    Notification create(Notification notification);

    String delete(Authentication authentication, DeleteNotification deleteNotification) throws LoginException;

    String deleteByRead(Authentication authentication) throws LoginException;

}
