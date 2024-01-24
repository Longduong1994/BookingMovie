package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotEmpty(message = "Username is not empty")
    @NotBlank(message = "Password is not blank")
    private String username;
    @NotEmpty(message = "Username is not empty")
    @NotBlank(message = "Password is not blank")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d).{8,}$",message = "Must be greater than 8 characters with 1 uppercase letter, 1 lowercase letter and 1 number")
    private String password;
    @Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message = "Email invalidate")
    private String email;
    @Pattern(regexp = "^(09|01[2|6|8|9])[0-9]{8}$", message = "Phone number invalid")
    private String phone;
    @NotNull(message = "The dateOfBirth field cannot be left blank.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
//    @NotEmpty(message = "Username is not empty")
//    @NotBlank(message = "Password is not blank")
//    private String city;
//    @NotNull(message = "Username is not empty")
//    private Long gender;
//    @NotEmpty(message = "Username is not empty")
//    @NotBlank(message = "Password is not blank")
//    private String captcha;
}
