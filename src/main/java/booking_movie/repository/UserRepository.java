package booking_movie.repository;

import booking_movie.constants.RoleName;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT U FROM User U WHERE :customerRole MEMBER OF U.roles AND U.username LIKE %:username%")
    Page<User> findAllCustomers(@Param("username") String username,
                                @Param("customerRole") Role customerRole,
                                Pageable pageable);


    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);


    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.roleName = :roleName AND u.username LIKE %:userName%")
    Page<User> findAllByRolesAndUserName(@Param("roleName") RoleName roleName, @Param("userName") String userName, Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.roleName = :roleName ")
    Page<User> findAllByRoles(@Param("roleName") RoleName roleName , Pageable pageable) ;
    @Query("SELECT COUNT(U) FROM User U JOIN U.roles R WHERE R.roleName = :roleName")
    Integer countUsersWithRole(@Param("roleName") String roleName);
}
