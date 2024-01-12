package booking_movie.config;

import booking_movie.mapper.ChairMapper;
import booking_movie.mapper.LocationMapper;
import booking_movie.mapper.RoomMapper;
import booking_movie.mapper.TheaterMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public LocationMapper locationMapper() {
        return Mappers.getMapper(LocationMapper.class) ;
    }

    @Bean
    public TheaterMapper theaterMapper() {
        return Mappers.getMapper(TheaterMapper.class) ;
    }

    @Bean
    public RoomMapper roomMapper() {
        return Mappers.getMapper(RoomMapper.class) ;
    }

    @Bean
    public ChairMapper chairMapper() {
        return Mappers.getMapper(ChairMapper.class) ;
    }
}
