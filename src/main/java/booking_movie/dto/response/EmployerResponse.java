package booking_movie.dto.response;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String card;
    private String avatar;
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String city;
    private String address;
    private String gender;
    private Boolean status;
    private String theater;
}