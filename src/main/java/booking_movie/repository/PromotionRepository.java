package booking_movie.repository;

import booking_movie.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    Boolean existsByEventCode(String code);
    @Query("SELECT P FROM Promotion P WHERE P.isDelete = :isDeleted AND P.eventName LIKE CONCAT('%', :searchTerm, '%')")
    Page<Promotion> findAllByIsDeleteAndEventName(@Param("isDeleted") Boolean isDeleted, @Param("searchTerm") String searchTerm, Pageable pageRequest);
}
