package booking_movie.mapper;

import booking_movie.dto.request.MenuRequestDto;
import booking_movie.dto.response.MenuResponseDto;
import booking_movie.entity.Menu;
import booking_movie.exception.DishException;
import booking_movie.exception.OrderException;
import booking_movie.service.dish.DishService;
import booking_movie.service.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuMapper {
    private final DishService dishService;
    private final OrderService orderService;
    public Menu menuRequestDtoIntoMenu(MenuRequestDto menuRequestDto) throws DishException, OrderException {
        return Menu.builder()
                .dish(dishService.findById(menuRequestDto.getDishId()))
                .quantity(menuRequestDto.getQuantity()).build();
    }
    public MenuResponseDto menuIntoMenuResponseDto(Menu menu){
        return MenuResponseDto.builder()
                .id(menu.getId())
                .price(menu.getDish().getPrice())
                .dishName(menu.getDish().getDishName())
                .quantity(menu.getQuantity())
                .build();
    };
}
