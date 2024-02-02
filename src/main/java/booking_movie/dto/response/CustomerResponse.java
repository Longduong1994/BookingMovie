package booking_movie.dto.response;
import booking_movie.constants.RankName;
import booking_movie.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private LocalDate createdAt;
    private Long point;
    private String avatar;
    private String city;
    private String address;
    private String gender;
    private String level;
    private Boolean status;
}
