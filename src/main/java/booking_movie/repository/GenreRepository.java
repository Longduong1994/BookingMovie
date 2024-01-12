package booking_movie.repository;
import booking_movie.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    Page<Genre> findAllByIsDeleted(Pageable pageable, Boolean isDeleted);
    Page<Genre> findAllByGenreNameContainingIgnoreCaseAndIsDeleted(Pageable pageable,String keyGenre,Boolean isDeleted);
    Genre findGenreByIdAndIsDeleted(Long idGenre, Boolean  isDeleted);
}
