package booking_movie.service.genre;
import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;
import booking_movie.entity.Genre;
import booking_movie.exception.GenreException;
import booking_movie.exception.LoginException;
import booking_movie.mapper.GenreMapper;
import booking_movie.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    @Override
    public Page<GenreResponseDto> getAllGenre(String keyGenre, Pageable pageable) {
        Page<Genre> listGenre;
        if (keyGenre.isEmpty()) {
            listGenre = genreRepository.findAllByIsDeleted(pageable, false);
        } else {
            listGenre = genreRepository.findAllByGenreNameContainingIgnoreCaseAndIsDeleted(pageable, keyGenre, false);
        }
        return listGenre.map(genreMapper::toResponseDto);
    }

    @Override
    public GenreResponseDto createGenre(GenreRequestDto genreRequestDto,Authentication authentication)throws LoginException {
        List<Genre> genreList = genreRepository.findAllByIsDeleted(false);
        if (genreList.stream().anyMatch(item -> item.getGenreName().equals(genreRequestDto.getGenreName()))) {
            throw new GenreException("Duplicate genre");
        }
        Genre genre = genreMapper.toEntity(genreRequestDto);
        return genreMapper.toResponseDto(genreRepository.save(genre));
    }

    @Override
    public void deleteGenre(Long idGenre) throws GenreException {
        Genre genreToDelete = genreRepository.findGenreByIdAndIsDeleted(idGenre, false);
        if(genreToDelete ==null){
            throw new GenreException("Genre not found");
        }else {
            genreToDelete.setIsDeleted(true);
            genreRepository.save(genreToDelete);
        }
    }
    @Override
    public GenreResponseDto getGenreById(Long idGenre) throws GenreException {
      Genre genre =  genreRepository.findGenreByIdAndIsDeleted(idGenre,false);
      if(genre !=null){
          return genreMapper.toResponseDto(genre);
      }
        throw new GenreException("Genre not found");
    }

    @Override
    public GenreResponseDto updateGenre(GenreRequestDto genreRequestDto,Long idGenre,Authentication authentication) throws GenreException {
        Genre genreEdit = genreRepository.findGenreByIdAndIsDeleted(idGenre,false);
        if(genreEdit !=null){
            genreEdit.setGenreName(genreRequestDto.getGenreName());
            return genreMapper.toResponseDto( genreRepository.save(genreEdit));
        }else {
            throw new GenreException("Genre not found");
        }

    }
}
