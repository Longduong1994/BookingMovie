package booking_movie.service.order;

import booking_movie.constants.Status;
import booking_movie.dto.request.OrderRequestDto;
import booking_movie.entity.Order;
import booking_movie.entity.User;
import booking_movie.exception.OrderException;
import booking_movie.exception.UserException;
import booking_movie.mapper.OrderMapper;
import booking_movie.repository.OrderRepository;
import booking_movie.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;

    /**
     * create order
     *
     * @author huyqt97
     */
    @Override
    public Order create(OrderRequestDto orderRequestDto, Authentication authentication) throws OrderException, UserException {
        User user = userService.userById(authentication);
        Optional<Order> order = orderRepository.findByUserAndStatus(user, Status.WAITING);
        if (order.isPresent()) {
            return order.get();
        }
        Order order1 = orderMapper.orderRequestDtoIntoOrder(orderRequestDto, user);
        return orderRepository.save(order1);
    }

    /**
     * find by id
     *
     * @author huyqt97
     */
    @Override
    public Order findById(Long id) throws OrderException {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            if (order.get().getIsDelete()) {
                return order.get();

            }
        }
        throw new OrderException("Order not found");
    }

    /**
     * update order
     *
     * @author huyqt97
     */
    @Override
    @Transactional
    public Order update(Authentication authentication) throws OrderException, UserException {
//        User user = userService.userById(authentication);
//        Optional<Order> order = orderRepository.findByUserAndStatus(user, Status.WAITING);
//        if (order.isEmpty()) {
//            throw new OrderException("order not found");
//        }
//        Order order1 = order.get();
        return null;
    }
}
