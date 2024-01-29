package booking_movie.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRetrievalDto {
    @NotNull(message = "Email không đưuọc để trống")
    @Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message = "Email sai định dạng")
    private String email;
}
