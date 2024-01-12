package booking_movie.config;

import booking_movie.mapper.LocationMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public LocationMapper locationMapper() {
        return Mappers.getMapper(LocationMapper.class) ;
    }
}
