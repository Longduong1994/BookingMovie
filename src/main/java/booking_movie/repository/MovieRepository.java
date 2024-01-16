package booking_movie.repository;

import booking_movie.constants.MovieStatus;
import booking_movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByIsDeleted(Boolean isDeleted);
    Page<Movie> findAllByIsDeleted(Pageable pageable, Boolean isDeleted);
    Page<Movie> findAllByMovieNameContainingIgnoreCaseAndIsDeleted(
            Pageable pageable, String movieName, Boolean isDeleted);

    Movie findMovieByIdAndIsDeleted(Long idMovie,Boolean isDeleted );
    Page<Movie> findAllByMovieNameContainingIgnoreCaseAndIsDeletedAndMovieStatus(
            Pageable pageable, String movieName, Boolean isDeleted, MovieStatus movieStatus);

}
