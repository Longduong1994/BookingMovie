package booking_movie.service.promotion;

import booking_movie.dto.request.promotion.PromotionRequestDto;
import booking_movie.dto.request.promotion.PromotionUpdateRequestDto;
import booking_movie.entity.Promotion;
import booking_movie.exception.PromtionException;
import booking_movie.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Promotion create(PromotionRequestDto promotionRequestDto, Authentication authentication) throws PromtionException, UserException;
    Promotion update(PromotionUpdateRequestDto promotionUpdateRequestDto,Authentication authentication) throws PromtionException, UserException;
    Page<Promotion> findAll(Integer page, Integer size, String search,Authentication authentication) throws PromtionException, UserException;
    String delete(Long id,Authentication authentication) throws PromtionException, UserException;
    Promotion findById(Long id) throws PromtionException;
    List <Promotion> findAllList();
}
