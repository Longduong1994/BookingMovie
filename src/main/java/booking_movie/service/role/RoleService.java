package booking_movie.service.role;


import booking_movie.entity.Role;
import booking_movie.entity.User;
import booking_movie.exception.NotFoundException;


public interface RoleService {

    Role findByRoleName(String roleName) throws NotFoundException;
    Role getRoleCustomer();
    Role getRoleManager();
    Role getRoleEmployee();
    Role getRoleAdmin();
    Boolean hasRoleAdmin(User user);
    Boolean hasRoleManager(User user);
    Boolean hasRoleEmployer(User user);
}
