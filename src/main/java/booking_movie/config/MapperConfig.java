package booking_movie.config;
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
public UserMapper userMapper(){
    return Mappers.getMapper(UserMapper.class);
}

  @Bean
    public LocationMapper locationMapper() {
        return Mappers.getMapper(LocationMapper.class) ;
    }
    @Bean
    public GenreMapper GenreMapper() {
        return Mappers.getMapper(GenreMapper.class) ;
    }

    @Bean
    public MovieMapper MovieMapper() {
        return Mappers.getMapper(MovieMapper.class) ;
    }
}
