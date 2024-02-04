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
<<<<<<< HEAD
    @Pattern(regexp = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$", message = "Số điện thoại không đúng định dạng")
=======
    @Pattern(regexp = "^(09|03[2|6|8|9])[0-9]{8}$", message = "Số điện thoại không đúng định dạng")
>>>>>>> 9f127b1fb7190fb813506cf495e156e1eee20a1a
    private String phone;
    @NotNull(message = "Ngày sinh không thể để trống")
    @NotNull(message = "Không được để trống.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
