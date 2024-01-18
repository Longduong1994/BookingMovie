package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    @NotEmpty(message = "Username is not empty")
    private String username;
    @NotEmpty(message = "Password is not empty")
    private String password;
    @NotEmpty(message = "Username is not empty")
    @NotBlank(message = "Password is not blank")
    private String captcha;
}
