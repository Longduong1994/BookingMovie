package booking_movie.service.menu;

import booking_movie.dto.request.MenuRequestDto;
import booking_movie.dto.response.MenuResponseDto;
import booking_movie.entity.Menu;
import booking_movie.entity.Order;
import booking_movie.entity.User;
import booking_movie.exception.DishException;
import booking_movie.exception.MenuException;
import booking_movie.exception.OrderException;
import booking_movie.exception.UserException;
import booking_movie.mapper.MenuMapper;
import booking_movie.repository.MenuRepository;
import booking_movie.service.order.OrderService;
import booking_movie.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final UserService userService;
    private final MenuMapper menuMapper;
    private final OrderService orderService;

    /**
     * push menu
     *
     * @author huyqt97
     */
    @Override
    public String push(MenuRequestDto menuRequestDto, Authentication authentication) throws UserException, DishException, OrderException, MenuException {
        User user = userService.userById(authentication);
        Menu menu = menuMapper.menuRequestDtoIntoMenu(menuRequestDto);
        if (menu.getOrder().getUser().equals(user)) {
            if (menu.getDish().getStatus()) {
                if (menu.getId() != null) {
                    Menu menu1 = findById(menu.getId());
                    menu1.setQuantity(menu1.getQuantity() + 1);
                    menuRepository.save(menu1);
                } else {
                    menu.setQuantity(1);
                    menuRepository.save(menu);
                }
            }
            throw new DishException("Dish quantity is not enough ");
        }
        throw new UserException("Account no permissions granted");
    }

    /**
     * sell menu
     *
     * @author huyqt97
     */
    @Override
    public String sell(MenuRequestDto menuRequestDto, Authentication authentication) throws UserException, DishException, OrderException, MenuException {
        User user = userService.userById(authentication);
        Menu menu = menuMapper.menuRequestDtoIntoMenu(menuRequestDto);
        if (menu.getOrder().getUser().equals(user)) {
            Menu menu1 = findById(menu.getId());
            if (menu1.getQuantity() >= 2) {
                menu1.setQuantity(menu1.getQuantity() - 1);
                menuRepository.save(menu1);
            } else {
                menuRepository.delete(menu1);
            }
        }
        throw new UserException("Account no permissions granted");
    }

    /**
     * find by id menu
     *
     * @author huyqt97
     */
    @Override
    public Menu findById(Long id) throws MenuException {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            return menu.get();
        }
        throw new MenuException("Menu not found");
    }

    /**
     * find by id menu
     *
     * @author huyqt97
     */
    @Override
    public List<MenuResponseDto> findAllByOrder(Long orderId) throws OrderException {
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
        Order order = orderService.findById(orderId);
        for (Menu m : menuRepository.findAllByOrder(order)) {
            menuResponseDtoList.add(menuMapper.menuIntoMenuResponseDto(m));
        }
        return menuResponseDtoList;
    }
}
