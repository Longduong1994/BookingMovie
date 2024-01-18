package booking_movie.repository;

import booking_movie.entity.Format;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormatRepository extends JpaRepository<Format,Long> {
    Format findFormatById(Long id);
}
