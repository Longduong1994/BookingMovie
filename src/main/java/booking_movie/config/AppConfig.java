package booking_movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
@Configuration
public class AppConfig {
    @Bean
    public ZoneId zoneId() {
        // You can customize the ZoneId as needed
        return ZoneId.systemDefault();
    }
}
