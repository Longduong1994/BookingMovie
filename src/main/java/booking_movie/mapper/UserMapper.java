package booking_movie.mapper;


import booking_movie.dto.request.CreateAccountDto;
import booking_movie.dto.request.RegisterRequestDto;
import booking_movie.dto.request.UpdateUserDto;
import booking_movie.dto.response.CustomerResponse;
import booking_movie.dto.response.EmployerResponse;
import booking_movie.dto.response.ManagerResponse;
import booking_movie.dto.response.UserResponseDto;
import booking_movie.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "theaterId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "theater", ignore = true)

    User toEntity(RegisterRequestDto requestDto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "theater", ignore = true)
    User toEntity(CreateAccountDto createAccountDto);
    @Mapping(target = "setRoles", ignore = true)
    @Mapping(target = "token", ignore = true)
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "theaterId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "theater", ignore = true)
   User toEntity(UpdateUserDto updateUserDto);
    @Mapping(target = "gender", expression = "java(mapGender(user))")
    CustomerResponse toCustomer(User user);

    @Mapping(target = "gender", expression = "java(mapGender(user))")
    ManagerResponse toManager(User user);
    @Mapping(target = "gender", expression = "java(mapGender(user))")
    EmployerResponse toEmployer(User user);
    default String mapGender(User user) {
        return (user.getGender() == 1) ? "Nam" : "Nữ";
    }
}
