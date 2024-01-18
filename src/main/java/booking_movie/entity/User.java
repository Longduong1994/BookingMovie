package booking_movie.entity;

import booking_movie.constants.RankName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String card;
    private Integer point;
    private String avatar;
    private String city;
    private String address;
    private Long gender;
    private Long theaterId;
    private LocalDate createdDate;
    @Enumerated(EnumType.STRING)
    private RankName level;
    private Boolean status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_ROLE",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
