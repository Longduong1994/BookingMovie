

package booking_movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.ZoneId;
@Configuration
public class AppConfig {
    @Bean
    public ZoneId zoneId() {
        return ZoneId.of("Asia/Ho_Chi_Minh");
    }
}