package booking_movie.repository;

import booking_movie.constants.RoleName;
import booking_movie.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleName roleName);


    @Query("SELECT Role FROM Role WHERE roleName ='ADMIN'")
    boolean checkRoleAdmin(Set<Role> roles);


}
