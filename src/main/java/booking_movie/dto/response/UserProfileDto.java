package booking_movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String avatar;
    private String city;
    private String address;
    private Long gender;
    private String level;
}
