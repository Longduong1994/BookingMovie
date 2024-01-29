package booking_movie.mapper;

import booking_movie.constants.RankName;
import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.response.*;
import booking_movie.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsersMapper {

    public User toEntity(RegisterRequestDto registerRequestDto){
        return User.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .phone(registerRequestDto.getPhone())
                .point(0)
                .dateOfBirth(registerRequestDto.getDateOfBirth())
                .createdDate(LocalDate.now())
                .level(RankName.COPPER)
                .status(true)
                .build();
    }

    public UserProfileDto toProfile(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .city(user.getCity())
                .address(user.getAddress())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .point(user.getPoint())
                .level(String.valueOf(user.getLevel()))
                .build();
    }

    public CustomerResponse toCustomer(User user) {
        return CustomerResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .city(user.getCity())
                .address(user.getAddress())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .point(user.getPoint())
                .level(String.valueOf(user.getLevel()))
                .status(user.getStatus())
                .createdAt(user.getCreatedDate())
                .build();
    }

    public EmployerResponse toEmployer(User user) {
        return EmployerResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .createdDate(user.getCreatedDate())
                .endDate(user.getEndDate())
                .theater(user.getTheaterName())
                .status(user.getStatus())
                .build();
    }
    public ManagerResponse toManager(User user) {
        return ManagerResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .createdDate(user.getCreatedDate())
                .endDate(user.getEndDate())
                .theater(user.getTheaterName())
                .status(user.getStatus())
                .build();
    }

    public User toEntity(CreateAccountDto createAccountDto){
        return User.builder()
                .username(createAccountDto.getUsername())
                .email(createAccountDto.getEmail())
                .phone(createAccountDto.getPhone())
                .dateOfBirth(createAccountDto.getDateOfBirth())
                .gender(createAccountDto.getGender())
                .build();
    }
}
