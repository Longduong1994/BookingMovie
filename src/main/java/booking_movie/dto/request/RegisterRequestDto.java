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
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    private String username;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d).{8,}$",message = "Must be greater than 8 characters with 1 uppercase letter, 1 lowercase letter and 1 number")
    private String password;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message = "Email invalidate")
    private String email;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp = "^(09|01[2|6|8|9])[0-9]{8}$", message = "Phone number invalid")
    private String phone;
    @NotNull(message = "The dateOfBirth field cannot be left blank.")
    @NotNull(message = "Không được để trống.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
