package booking_movie.service.promotion;

import booking_movie.dto.request.promotion.PromotionRequestDto;
import booking_movie.dto.request.promotion.PromotionUpdateRequestDto;
import booking_movie.entity.Promotion;
import booking_movie.exception.PromtionException;
import org.springframework.security.core.Authentication;

public interface PromotionService {
    Promotion create(PromotionRequestDto promotionRequestDto, Authentication authentication)throws PromtionException;
    Promotion update(PromotionUpdateRequestDto promotionUpdateRequestDto,Authentication authentication) throws PromtionException;
}
