package booking_movie.mapper;

import booking_movie.dto.request.promotion.PromotionRequestDto;
import booking_movie.dto.request.promotion.PromotionUpdateRequestDto;
import booking_movie.entity.Promotion;
import booking_movie.exception.PromtionException;
import booking_movie.repository.PromotionRepository;
import booking_movie.service.upload_image.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PromotionMapper {
    private final PromotionRepository promotionRepository;
    private final UploadFileService uploadFileService;
    public Promotion promotionRequestDtoIntoPromotion(PromotionRequestDto promotionRequestDto, String userName,String code) {
        return Promotion.builder()
                .eventName(promotionRequestDto.getEventName())
                .image(uploadFileService.uploadFile(promotionRequestDto.getImage()))
                .eventCode(code)
                .salePrice(promotionRequestDto.getSalePrice())
                .startDate(promotionRequestDto.getStartDate())
                .endDate(promotionRequestDto.getEndDate())
                .createUser(userName)
                .updateUser(userName)
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .description(promotionRequestDto.getDescription())
                .isDelete(false).build();
    }

    public Promotion promotionUpdateRequestDtoIntoPromotion(PromotionUpdateRequestDto promotionUpdateRequestDto, String userName) throws PromtionException {
        Optional<Promotion> promotion = promotionRepository.findById(promotionUpdateRequestDto.getId());
        if (promotion.isPresent()) {
            Promotion promotion1 = promotion.get();
            promotion1.setEventName(promotionUpdateRequestDto.getEventName());
            promotion1.setDescription(promotionUpdateRequestDto.getDescription());
            promotion1.setSalePrice(promotionUpdateRequestDto.getSalePrice());
            promotion1.setStartDate(promotionUpdateRequestDto.getStartDate());
            promotion1.setEndDate(promotionUpdateRequestDto.getEndDate());
            promotion1.setUpdateTime(LocalDate.now());
            promotion1.setUpdateUser(userName);
            return promotion1;
        }
        throw new PromtionException("Promotion not found");
    }
}
