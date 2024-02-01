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
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d).{8,}$",message = "Phải lớn hơn 8 ký tự với 1 chữ hoa, 1 chữ thường và 1 số")
    private String password;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message = "Email không đúng định dạng")
    private String email;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp = "^(09|01[2|6|8|9])[0-9]{8}$", message = "Số điện thoại không đúng định dạng")
    private String phone;
    @NotNull(message = "Ngày sinh không thể để trống")
    @NotNull(message = "Không được để trống.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
}
