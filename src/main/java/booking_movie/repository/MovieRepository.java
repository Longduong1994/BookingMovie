package booking_movie.repository;
import booking_movie.constants.MovieStatus;
import booking_movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Page<Movie> findAllByIsDeleted(Pageable pageable, Boolean isDeleted);
    Page<Movie> findAllByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(Pageable pageable,String keySearch,Boolean isDeleted);

    Page<Movie> findAllByMovieNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeletedAndMovieStatus(Pageable pageable, String keySearch, Boolean isDeleted, MovieStatus movieStatus);
}