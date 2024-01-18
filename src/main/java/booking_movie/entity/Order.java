package booking_movie.entity;

import booking_movie.constants.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "BOOKING_MOVIE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theaterName;
    private String roomName;
    private String locationName;
    private String movieName;
    private String imageMovie;
    private String rated;
    private String format;
    @Column(columnDefinition = "TIME")
    private LocalTime startTime;
    @Column(columnDefinition = "DATE")
    private LocalDateTime bookingDate;
    private String code;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TICKET_CHAIR",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "chair_id"))
    private Set<Chair> chairs;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    private Double menuPrice;

    private Double moviePrice;

    private Double total;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String createUser;

    private LocalDate createTime;

    private String updateUser;

    private LocalDate updateTime;

    private Boolean isDelete;

    private Long theater;
}
