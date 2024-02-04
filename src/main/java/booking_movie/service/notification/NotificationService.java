package booking_movie.service.notification;

import booking_movie.dto.request.DeleteNotification;
import booking_movie.dto.response.NotificationResponse;
import booking_movie.entity.Notification;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface NotificationService {
    List<NotificationResponse> getNotification(Authentication authentication) throws LoginException;

    Notification create(Notification notification);
    String changeStatusRead(Long id) throws NotFoundException, CustomsException;

    String delete(Authentication authentication, DeleteNotification deleteNotification) throws LoginException;

    String deleteByRead(Authentication authentication) throws LoginException;

}
