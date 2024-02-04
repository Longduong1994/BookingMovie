package booking_movie.service.order;


import booking_movie.dto.request.OrderRequestDto;
import booking_movie.dto.response.OrderResponseDto;
import booking_movie.entity.*;
import booking_movie.exception.*;
import booking_movie.repository.*;
import booking_movie.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import booking_movie.entity.Order;
import booking_movie.entity.User;
import booking_movie.repository.OrderRepository;





@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public Long spendingByYear(Long year, Authentication authentication) throws LoginException {
        User user  = userService.getUser(authentication);
        return orderRepository.getTotalSumByUserIdAndYear(user.getId(), year);
    }

    private final UserService userService;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ChairRepository chairRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto findByOrderId(Long id) throws NotFoundException {
        Optional<Order> orderFind = orderRepository.findById(id);
        if (!orderFind.isPresent()) {
            throw new NotFoundException("Không tìm thấy đơn hàng");
        }
        Order order = orderFind.get();
        return OrderResponseDto.builder()
                .id(order.getId())
                .movieName(order.getMovieName())
                .code(order.getCode())
                .movieImage(order.getImageMovie())
                .startTime(order.getStartTime())
                .bookingDate(order.getBookingDate())
                .rated(order.getRated())
                .locationName(order.getLocationName())
                .chairs(order.getChairs().stream().map(item -> item.getChairName()).collect(Collectors.toSet()))
                .theaterName(order.getTheaterName())
                .roomName(order.getRoomName())
                .paymentMethod(order.getPayment().getPaymentMethod())
                .total(order.getTotal())
                .build();
    }

    @Override
    public Order findByCode(String code) {
        Optional<Order> order = orderRepository.findByCode(code);
        return order.get();
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    @Override
    public OrderResponseDto create(Authentication authentication, OrderRequestDto orderRequestDto) throws LoginException, CustomsException, NotFoundException {
        User user = userService.getUser(authentication);
        Room room = roomRepository.findById(orderRequestDto.getRoomId()).orElseThrow(() -> new NotFoundException("Room not found"));
        Movie movie = movieRepository.findById(orderRequestDto.getMovieId()).orElseThrow(() -> new NotFoundException("Movie not found"));

        Set<Chair> chairSet = orderRequestDto.getChairIds().stream()
                .map(chair -> {
                    try {
                        return chairRepository.findById(chair).orElseThrow(() -> new NotFoundException("Chair not found"));
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
        Order order = new Order();
        String code = UUID.randomUUID().toString().substring(0, 12);
        order.setCode(code);
        order.setUser(user);
        order.setTotal(orderRequestDto.getTotal());
        order.setLocationName(orderRequestDto.getLocation());
        order.setTheaterName(orderRequestDto.getTheater());
        order.setRoomName(room.getRoomName());
        order.setImageMovie(movie.getMovieImage());
        order.setMovieName(movie.getMovieName());
        order.setCreateTime(LocalDate.now());
        order.setRated(movie.getRated());
        order.setPoint(orderRequestDto.getPoint());
        order.setStartTime(orderRequestDto.getStartTime());
        order.setBookingDate(orderRequestDto.getBookingDate());
        order.setChairs(chairSet);


        //  menu


        orderRepository.save(order);


        return OrderResponseDto.builder()
                .id(order.getId())
                .movieName(order.getMovieName())
                .code(order.getCode())
                .movieImage(order.getImageMovie())
                .startTime(order.getStartTime())
                .bookingDate(order.getBookingDate())
                .rated(order.getRated())
                .locationName(order.getLocationName())
                .chairs(order.getChairs().stream().map(item -> item.getChairName()).collect(Collectors.toSet()))
                .theaterName(order.getTheaterName())
                .roomName(order.getRoomName())
                .total(order.getTotal())
                .build();
    }


//     private final OrderRepository orderRepository;
//     private final UserService userService;
//     private final OrderMapper orderMapper;

//     /**
//      * create order
//      *
//      * @author huyqt97
//      */
//     @Override
//     public Order create(OrderRequestDto orderRequestDto, Authentication authentication) throws OrderException, UserException {
//         User user = userService.userById(authentication);
//         Optional<Order> order = orderRepository.findByUserAndStatus(user, Status.WAITING);
//         if (order.isPresent()) {
//             return order.get();
//         }
//         Order order1 = orderMapper.orderRequestDtoIntoOrder(orderRequestDto, user);
//         return orderRepository.save(order1);
//     }

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

//     /**
//      * update order
//      *
//      * @author huyqt97
//      */
//     @Override
//     @Transactional
//     public Order update(Authentication authentication) throws OrderException, UserException {
// //        User user = userService.userById(authentication);
// //        Optional<Order> order = orderRepository.findByUserAndStatus(user, Status.WAITING);
// //        if (order.isEmpty()) {
// //            throw new OrderException("order not found");
// //        }
// //        Order order1 = order.get();
//         return null;
//     }


    @Override
    public Double sumTotalSpending(Authentication authentication) throws UserException {
         User user = userService.userById(authentication);
        return orderRepository.getTotalUser(user);
    }

    @Override
    public Page<Order> findAllByUser(Integer page,Integer size,Authentication authentication) throws UserException {
         User user = userService.userById(authentication);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return orderRepository.findAllByUser(user, pageRequest);
    }

    @Override
    public Page<Order> findAllLocalDate(Integer page, Integer size, LocalDate localDate) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return orderRepository.findAllByBookingDate(localDate,pageRequest);
    }

    @Override
    public Page<Order> findAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return orderRepository.findAll(pageRequest);
    }

    @Override
    public Double sumTotalRevenue(Authentication authentication) throws UserException {
         User user = userService.userById(authentication);
         if(orderRepository.sumTotalRevenue()!= null){
        return orderRepository.sumTotalRevenue();
         }else {
             return (double) 0;
         }
    }

    @Override
    public Double sumTotalCurrentYear(Authentication authentication) throws UserException {
         userService.userById(authentication);
        Integer currentYear = Year.now().getValue();
         if(orderRepository.sumTotalYear(currentYear)!= null) {
             return orderRepository.sumTotalYear(currentYear);
         }else {
             return (double) 0;
         }
    }
}
