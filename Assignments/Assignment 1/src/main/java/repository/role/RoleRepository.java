package repository.role;

import model.Role;

public interface RoleRepository {

    void addRole(String role);

    Role findRoleById(int roleId);

    Role findRoleByName(String name);
}
