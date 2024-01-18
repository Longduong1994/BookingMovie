package booking_movie.service.role;


import booking_movie.entity.Role;
import booking_movie.entity.User;


public interface RoleService {
    Role getRoleCustomer();
    Role getRoleManager();
    Role getRoleEmployee();
    Role getRoleAdmin();
    Boolean hasRoleAdmin(User user);
    Boolean hasRoleManager(User user);
    Boolean hasRoleEmployer(User user);
}
