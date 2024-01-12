package booking_movie.dto.request;

import java.time.LocalDate;

public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
}
