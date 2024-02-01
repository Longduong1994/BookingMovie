package booking_movie.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDto {
    @NotEmpty(message = "Tên đăng nhập không thể để trống")
    @NotBlank(message = "Tên đăng nhập không thể để trống")
    private String username;
    @Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message = "Email không đúng định dạng")
    private String email;
    @Pattern(regexp = "^(09|01[2|6|8|9])[0-9]{8}$", message = "Số điện thoại không đúng định dạng")
    private String phone;
    @NotNull(message = "Ngày sinh không thể để trống")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "Quyền đăng người dùng không thể để trống")
    @NotBlank(message = "Quyền đăng người dùng không thể để trống")
    private String role;
    @NotNull(message = "Giới tính không thể để trống")
    private String gender;
    @NotNull(message = "Mã rạp chiếu không thể để trống")
    private Long theaterId;
}
