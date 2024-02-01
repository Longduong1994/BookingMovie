package booking_movie.mapper;

import booking_movie.constants.Status;
import booking_movie.dto.request.OrderRequestDto;
import booking_movie.entity.Order;
import booking_movie.entity.User;
import booking_movie.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class OrderMapper {
    private PromotionRepository promotionRepository;
    public Order orderRequestDtoIntoOrder(OrderRequestDto orderRequestDto, User user){
        return Order.builder()
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .status(Status.WAITING)
                .createUser(user.getUsername())
                .payment(null)
                .isDelete(false).build();
    }
}
