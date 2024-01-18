package booking_movie.service.order;

import booking_movie.dto.request.OrderRequestDto;
import booking_movie.dto.response.OrderResponseDto;
import booking_movie.entity.*;
import booking_movie.exception.CustomsException;
import booking_movie.exception.LoginException;
import booking_movie.exception.NotFoundException;
import booking_movie.repository.*;
import booking_movie.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final LocationRepository locationRepository;
    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ChairRepository chairRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public OrderResponseDto create(Authentication authentication, OrderRequestDto orderRequestDto) throws LoginException, CustomsException, NotFoundException {
        User user = userService.getUser(authentication);
        Location location = locationRepository.findById(orderRequestDto.getLocationId()).orElseThrow(() -> new NotFoundException("Location not found"));
        Theater theater = theaterRepository.findById(orderRequestDto.getTheaterId()).orElseThrow(() -> new NotFoundException("Theater not found"));;
        Room room = roomRepository.findById(orderRequestDto.getRoomId()).orElseThrow(() -> new NotFoundException("Room not found"));;
        Movie movie = movieRepository.findById(orderRequestDto.getMovieId()).orElseThrow(() -> new NotFoundException("Movie not found"));;
        Payment payment = paymentRepository.findById(orderRequestDto.getPaymentId()).orElseThrow(() -> new NotFoundException("Payment not found"));;
        Set<Chair> chairSet = orderRequestDto.getChairIds().stream()
                .map(chair -> {
                    try {
                        return chairRepository.findById(chair).orElseThrow(()-> new NotFoundException("Chair not found"));
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
        Order order = new Order();
        String code = UUID.randomUUID().toString().substring(0, 12);
        order.setCode(code);
        order.setUser(user);
        order.setLocationName(location.getLocationName());
        order.setTheaterName(theater.getTheaterName());
        order.setRoomName(room.getRoomName());
        order.setImageMovie(movie.getMovieImage());
        order.setMovieName(movie.getMovieName());
        order.setRated(movie.getRated());
        order.setStartTime(orderRequestDto.getStartTime());
        order.setBookingDate(orderRequestDto.getBookingDate());
        order.setPayment(payment);
        order.setChairs(chairSet);
        order.setTotal(movie.getPrice() * chairSet.size());


        //  menu


        orderRepository.save(order);


        return OrderResponseDto.builder()
                .id(order.getId())
                .movieName(order.getMovieName())
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
}
