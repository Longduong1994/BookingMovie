package booking_movie.service.role;

import booking_movie.constants.RoleName;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleCustomer() {
        return roleRepository.findByRoleName(RoleName.CUSTOMER).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    public  Boolean hasRoleAdmin(User user) {
        if (user != null && user.getRoles() != null) {
            return user.getRoles().stream().anyMatch(role -> RoleName.ADMIN.equals(role.getRoleName()));
        }
        return false;
    }

}
