package booking_movie.dto.response;

import booking_movie.constants.RankName;
import booking_movie.entity.Role;
import jakarta.persistence.*;

import java.util.Set;

public class CustomerResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String card;
    private Integer point;
    private String level;
    private Boolean status;
    private String role;
}
