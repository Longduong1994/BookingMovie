package booking_movie.service.notification;

import booking_movie.dto.request.DeleteNotification;
import booking_movie.dto.response.NotificationResponse;
import booking_movie.entity.Notification;
import booking_movie.entity.User;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.mapper.NotificationMapper;
import booking_movie.repository.NotificationRepository;
import booking_movie.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationResponse> getNotification(Authentication authentication) throws LoginException {
        User user = userService.getUser(authentication);
        List<Notification> notifications = notificationRepository.findByUserId(user.getId());
        return notifications.stream().map(notificationMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public String changeStatusRead(Long id) throws NotFoundException, CustomsException {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (!notification.isPresent()) {
            throw new NotFoundException("Không tìm thấy thông báo.");
        }
        notification.get().setRead(true);
        notificationRepository.save(notification.get());
        return "Đã đọc thông báo";
    }

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }
    @Transactional
    @Override
    public String delete(Authentication authentication, DeleteNotification deleteNotification) throws LoginException {
        User user = userService.getUser(authentication);
        try {
            notificationRepository.deleteByIdIn(deleteNotification.getIds());
            return "Xóa thành công";
        } catch (Exception e) {
            // Xử lý lỗi
            return "Xảy ra lỗi khi xóa thông báo: " + e.getMessage();
        }
    }

    @Transactional
    @Override
    public String deleteByRead(Authentication authentication) throws LoginException {
        User user = userService.getUser(authentication);
        notificationRepository.deleteByReadAndUsers(false,user);
        return "Xóa thành công";
    }
}
