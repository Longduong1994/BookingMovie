package booking_movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String city;
    private String address;
    private Long gender;
    private String phone;
}
