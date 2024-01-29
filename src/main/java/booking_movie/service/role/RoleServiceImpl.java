package booking_movie.service.role;

import booking_movie.constants.RoleName;
import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.exception.NotFoundException;
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
    public Role getRoleManager() {
        return roleRepository.findByRoleName(RoleName.MANAGER).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    public Role getRoleEmployee() {
        return roleRepository.findByRoleName(RoleName.EMPLOYER).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    public Role getRoleAdmin() {
        return roleRepository.findByRoleName(RoleName.ADMIN).orElseThrow(()-> new RuntimeException("Role not found"));
    }

    @Override
    public  Boolean hasRoleAdmin(User user) {
        if (user != null && user.getRoles() != null) {
            return user.getRoles().stream().anyMatch(role -> RoleName.ADMIN.equals(role.getRoleName()));
        }
        return false;
    }

    @Override
    public Boolean hasRoleManager(User user) {
        if (user != null && user.getRoles() != null) {
            return user.getRoles().stream().anyMatch(role -> RoleName.MANAGER.equals(role.getRoleName()));
        }
        return false;
    }


    @Override
    public Boolean hasRoleEmployer(User user) {
        if (user != null && user.getRoles() != null) {
            return user.getRoles().stream().anyMatch(role -> RoleName.EMPLOYER.equals(role.getRoleName()));
        }
        return false;
    }


    @Override
    public Role findByRoleName(String roleName) throws NotFoundException {
        RoleName role;
        switch (roleName.toUpperCase()){
            case "MANAGER":
                role = RoleName.MANAGER;
                break;
            case "EMPLOYER":
                role = RoleName.EMPLOYER;
                break;
            default:
                throw new NotFoundException("Chưa có role này");

        }
        return roleRepository.findByRoleName(RoleName.CUSTOMER).orElseThrow(()-> new RuntimeException("Role not found"));
    }
}
