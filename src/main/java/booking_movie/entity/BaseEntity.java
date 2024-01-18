package booking_movie.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import java.time.LocalDateTime;
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Column(name = "CREATE_TIME")
    private LocalDateTime createDttm;
    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateDttm;
    @Column(name = "CREATE_USER")
    private String createUser;
    @Column(name = "UPDATE_USER")
    private String updateUser;
    @Column(name = "UPDATE_CLASS")
    private Integer updateClass;
    @Column(name = "DELETE_FLAG")
    private Boolean isDeleted;
}