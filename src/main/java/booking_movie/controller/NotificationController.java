package booking_movie.controller;

import booking_movie.dto.request.DeleteNotification;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.service.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> findAll(Authentication authentication) throws LoginException {
        return new ResponseEntity<>(notificationService.getNotification(authentication),HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> remove(@RequestBody DeleteNotification deleteNotification, Authentication authentication) throws LoginException {
        return new ResponseEntity<>(notificationService.delete(authentication, deleteNotification), HttpStatus.OK);
    }

    @PatchMapping("/delete/read")
    public ResponseEntity<?> removeRead( Authentication authentication) throws LoginException {
        return new ResponseEntity<>(notificationService.deleteByRead(authentication), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable String id) throws CustomsException, NotFoundException {
        Long notificationId = Long.parseLong(id);
        return new ResponseEntity<>(notificationService.changeStatusRead(notificationId),HttpStatus.OK);
    }

}
