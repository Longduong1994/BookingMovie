package booking_movie.config;


import booking_movie.mapper.ChairMapper;
import booking_movie.mapper.RoomMapper;
import booking_movie.mapper.TheaterMapper;
import booking_movie.mapper.GenreMapper;
import booking_movie.mapper.LocationMapper;
import booking_movie.mapper.MovieMapper;
import booking_movie.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
    @Bean
    public GenreMapper genreMapper() {
        return Mappers.getMapper(GenreMapper.class) ;
    }



}
