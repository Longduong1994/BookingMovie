package booking_movie.mapper;

import booking_movie.dto.response.NotificationResponse;
import booking_movie.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
   public NotificationResponse toResponse(Notification notification){
     return    NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .read(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
