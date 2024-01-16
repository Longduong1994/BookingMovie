package booking_movie.service.promotion;

import booking_movie.constants.RoleName;
import booking_movie.dto.request.promotion.PromotionRequestDto;
import booking_movie.dto.request.promotion.PromotionUpdateRequestDto;
import booking_movie.entity.Promotion;
import booking_movie.entity.User;
import booking_movie.exception.PromtionException;
import booking_movie.mapper.PromotionMapper;
import booking_movie.repository.PromotionRepository;
import booking_movie.security.user_principle.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    /**
     *create promotion
     *
     * @author huyqt97
     */
    @Override
    public Promotion create(PromotionRequestDto promotionRequestDto, Authentication authentication) throws PromtionException {
        User user = userById(authentication);
        if(promotionRequestDto.getEventCode().equals("")){
            promotionRequestDto.setEventCode(randomCode(promotionRequestDto,authentication));
            return promotionRepository.save(promotionMapper.promotionRequestDtoIntoPromotion(promotionRequestDto,user.getUsername()));
        }else {
            return promotionRepository.save(promotionMapper.promotionRequestDtoIntoPromotion(promotionRequestDto,user.getUsername()));
        }
    }

    /**
     *update promotion
     *
     * @author huyqt97
     */
    @Override
    public Promotion update(PromotionUpdateRequestDto promotionUpdateRequestDto, Authentication authentication) throws PromtionException {
        User user = userById(authentication);
        Optional<Promotion> promotion = promotionRepository.findById(promotionUpdateRequestDto.getId());
        if(promotion.isEmpty()){
            throw new PromtionException("Promotion not found");
        }
        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if(isAdminOrManager){
            return promotionMapper.promotionUpdateRequestDtoIntoPromotion(promotionUpdateRequestDto,user.getUsername());
        }throw new PromtionException("no permissions granted");
    }

    /**
     * find By id user
     * return {@Link User}
     *
     * @author huyqt97
     */
    public User userById(Authentication authentication)throws PromtionException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (userPrincipal == null || userPrincipal.getUser() == null) {
            throw new PromtionException("User not found");
        }

        return userPrincipal.getUser();
    }
    /**
     * random code
     *
     * @author huyqt97
     */
    public String randomCode(PromotionRequestDto promotionRequestDto, Authentication authentication) throws PromtionException {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            str.append(digit);
        }
        if(promotionRepository.existsByEventCode(str.toString())){
            create(promotionRequestDto,authentication);
        }
        return str.toString();
    }
}
