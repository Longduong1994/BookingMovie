package booking_movie.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ManagerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String card;
    private String avatar;
    private String city;
    private LocalDate createdDate;
    private String address;
    private String gender;
    private Boolean status;
}