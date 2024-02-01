package booking_movie.service.genre;
import booking_movie.constants.DateTimeComponent;
import booking_movie.dto.request.GenreRequestDto;
import booking_movie.dto.response.GenreResponseDto;
import booking_movie.entity.Genre;
import booking_movie.entity.User;
import booking_movie.exception.CustomsException;
import booking_movie.exception.GenreException;
import booking_movie.exception.LoginException;
import booking_movie.mapper.GenreMapper;
import booking_movie.repository.GenreRepository;
import booking_movie.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final UserService userService;
    private final DateTimeComponent dateTimeComponent;

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
    public List<GenreResponseDto> findAllNoSearch() {
        List<Genre> list = genreRepository.findAll();
        return list.stream().map(genreMapper::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public GenreResponseDto findById(Long id) throws CustomsException {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new CustomsException("Thể loại không tồn tại"));
        return genreMapper.toResponseDto(genre);
    }

    @Override
    public GenreResponseDto createGenre(GenreRequestDto genreRequestDto, Authentication authentication) throws LoginException {
        User user= userService.getUser(authentication);
        List<Genre> genreList = genreRepository.findAllByIsDeleted(false);
        if (genreList.stream().anyMatch(item -> item.getGenreName().equals(genreRequestDto.getGenreName()))) {
            throw new GenreException("Tên thể loại đã tồn tại");
        }
        Genre genre = genreMapper.toEntity(genreRequestDto);
        genre.setCreateDttm(dateTimeComponent.now());
        genre.setCreateUser(user.getUsername());
        genre.setUpdateClass(1);
        genre.setIsDeleted(false);
        return genreMapper.toResponseDto(genreRepository.save(genre));
    }

    @Override
    public void deleteGenre(Long idGenre) throws GenreException {
        Genre genreToDelete = genreRepository.findGenreByIdAndIsDeleted(idGenre, false);
        if(genreToDelete ==null){
            throw new GenreException("Thể loại không tồn tại");
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
        throw new GenreException("Thể loại không tồn tại");
    }
    @Override
    public GenreResponseDto updateGenre(GenreRequestDto genreRequestDto,Long idGenre,Authentication authentication) throws GenreException, LoginException {
        User user= userService.getUser(authentication);
        Genre genreEdit = genreRepository.findGenreByIdAndIsDeleted(idGenre,false);
        if(genreEdit !=null){
            genreEdit.setGenreName(genreRequestDto.getGenreName());
            genreEdit.setUpdateUser(user.getUsername());
            genreEdit.setUpdateClass(2);
            genreEdit.setUpdateDttm(dateTimeComponent.now());

            return genreMapper.toResponseDto( genreRepository.save(genreEdit));
        }else {
            throw new GenreException("Thể loại không tồn tại");
        }

    }
}
