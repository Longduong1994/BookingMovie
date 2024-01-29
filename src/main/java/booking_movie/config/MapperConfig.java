package booking_movie.config;


import booking_movie.mapper.GenreMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public GenreMapper genreMapper() {
        return Mappers.getMapper(GenreMapper.class) ;
    }



}
