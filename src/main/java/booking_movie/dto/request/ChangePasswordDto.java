package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    private String oldPassword;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d).{8,}$",message = "Must be greater than 8 characters with 1 uppercase letter, 1 lowercase letter and 1 number")
    private String newPassword;
    @NotEmpty(message = "Không được để trống")
    @NotBlank(message = "Không được có khoảng trắng")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d).{8,}$",message = "Must be greater than 8 characters with 1 uppercase letter, 1 lowercase letter and 1 number")
    private String confirmNewPassword;

}
