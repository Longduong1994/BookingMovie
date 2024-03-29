package booking_movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "PROMOTION")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private String image;

    private String eventCode;

    private String description;

    private Double salePrice;

    private LocalDate startDate;

    private LocalDate endDate;
//
    private String createUser;

    private LocalDate createTime;

    private String updateUser;

    private LocalDate updateTime;

    private Boolean isDelete;

//
}
