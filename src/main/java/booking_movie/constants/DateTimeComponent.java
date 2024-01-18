package booking_movie.constants;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class DateTimeComponent {
    private final ZoneId zoneId;

    public LocalDateTime now() {
        return LocalDateTime.now(this.zoneId);
    }
}