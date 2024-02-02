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
import booking_movie.repository.OrderRepository;
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
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    /**
     * push menu
     *
     * @author huyqt97
     */
    @Override
    public String push(List<MenuRequestDto> listMenuRequestDto,Long orderId) throws UserException, DishException, OrderException, MenuException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent()) {
            throw new UserException("Đơn hàng không tồn tại");
        }
        for (MenuRequestDto menuRequestDto : listMenuRequestDto) {
            Menu menu = menuMapper.menuRequestDtoIntoMenu(menuRequestDto);
            menu.setOrder(order.get());
            menuRepository.save(menu);
        }
        return "thêm đồ ăn thành công";
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
        throw new UserException("Tài khoản không được cấp quyền");
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
        throw new MenuException("Menu không tồn tại");
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
        List<Menu> menuList = menuRepository.findAllByOrder(order);
        for (int i = menuList.size() - 1; i >= 0; i--) {
            Menu m = menuList.get(i);
        for (Menu m : menuRepository.findAllByOrder(orderId)) {
            menuResponseDtoList.add(menuMapper.menuIntoMenuResponseDto(m));
        }
        return menuResponseDtoList;
    }
}
