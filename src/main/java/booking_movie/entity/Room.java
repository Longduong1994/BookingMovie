package booking_movie.entity;

import booking_movie.constants.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ROOM" )
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private Integer numberOfSeatsInARow ;
    private Integer numberOfSeatsInAColumn ;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
    public String createUser;
    public String updateUser;
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theater_id")
    @JsonIgnore
    private Theater theater;
    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<TimeSlot> timeSlots ;

}
