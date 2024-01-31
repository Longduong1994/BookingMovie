package booking_movie.service.promotion;

import booking_movie.constants.RoleName;
import booking_movie.dto.request.promotion.PromotionRequestDto;
import booking_movie.dto.request.promotion.PromotionUpdateRequestDto;
import booking_movie.entity.Notification;
import booking_movie.entity.Promotion;
import booking_movie.entity.User;
import booking_movie.exception.PromtionException;
import booking_movie.exception.UserException;
import booking_movie.mapper.PromotionMapper;
import booking_movie.repository.PromotionRepository;
import booking_movie.service.notification.NotificationService;
import booking_movie.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final UserService userService;
    private final NotificationService notificationService;

    /**
     * create promotion
     *
     * @author huyqt97
     */
    @Override
    public Promotion create(PromotionRequestDto promotionRequestDto, Authentication authentication) throws PromtionException, UserException {
        User user = userService.userById(authentication);
        String code = randomCode(promotionRequestDto, authentication);
        String message = "Chào xuân 2024";

        Notification notification = new Notification();
        notification.setTitle(promotionRequestDto.getDescription());
        notification.setMessage(message);
        notification.setCreatedAt(LocalDate.now());
        notification.setRead(true);
        notificationService.create(notification);
        return promotionRepository.save(promotionMapper.promotionRequestDtoIntoPromotion(promotionRequestDto, user.getUsername(),code));

    }

    /**
     * update promotion
     *
     * @author huyqt97
     */
    @Override
    public Promotion update(PromotionUpdateRequestDto promotionUpdateRequestDto, Authentication authentication) throws PromtionException, UserException {
        User user = userService.userById(authentication);
        boolean isAdminOrManager = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN) || role.getRoleName().equals(RoleName.MANAGER));
        if (isAdminOrManager) {
            return promotionMapper.promotionUpdateRequestDtoIntoPromotion(promotionUpdateRequestDto, user.getUsername());
        }
        throw new PromtionException("Không có quyền nào được cấp");
    }

    /**
     * find all promotion
     *
     * @author huyqt97
     */
    @Override
    public Page<Promotion> findAll(Integer page, Integer size, String search, Authentication authentication) throws PromtionException, UserException {
        return promotionRepository.findAllByIsDeleteAndEventName(false, search, PageRequest.of(page, size));
    }

    @Override
    public String delete(Long id, Authentication authentication) throws PromtionException, UserException {
        User user = userService.userById(authentication);
        Optional<Promotion> promotion = promotionRepository.findById(id);
        if (promotion.isPresent()) {
            Promotion promotion1 = promotion.get();
            promotion1.setUpdateUser(user.getUsername());
            promotion1.setUpdateTime(LocalDate.now());
            promotion1.setIsDelete(true);
            promotionRepository.save(promotion1);
            return "Delete success";
        }
        throw new PromtionException("Không tìm thấy khuyến mãi");
    }

    /**
     * random code
     *
     * @author huyqt97
     */
    public String randomCode(PromotionRequestDto promotionRequestDto, Authentication authentication) throws PromtionException, UserException {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            str.append(digit);
        }
        if (promotionRepository.existsByEventCode(str.toString())) {
            create(promotionRequestDto, authentication);
        }
        return str.toString();
    }
}
