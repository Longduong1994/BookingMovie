package booking_movie.service.role;

import booking_movie.entity.Role;
import booking_movie.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRole(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
