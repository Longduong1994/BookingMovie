package booking_movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String avatar;
    private String city;
    private String address;
    private String gender;
    private String level;
    private Long point;
}
