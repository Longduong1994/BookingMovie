package booking_movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @NotEmpty(message = "Thành phố không thể để trống")
    private String city;
    @NotEmpty(message = "Địa chỉ không thể để trống")
    private String address;
    @NotNull(message = "Giới tính không thể để trồng")
    private String gender;
    @Pattern(regexp = "^(09|01[2|6|8|9])[0-9]{8}$", message = "Số điện thoại sai định dạng.")
    private String phone;
}
