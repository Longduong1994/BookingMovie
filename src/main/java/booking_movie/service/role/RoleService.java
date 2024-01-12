package booking_movie.service.role;


import booking_movie.entity.Role;
import booking_movie.entity.User;


public interface RoleService {
    Role getRoleCustomer();
    Boolean hasRoleAdmin(User user);

}
