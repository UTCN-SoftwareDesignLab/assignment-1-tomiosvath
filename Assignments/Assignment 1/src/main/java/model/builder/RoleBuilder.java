package model.builder;

import model.Role;
import model.User;

public class RoleBuilder {

    private Role role;

    public RoleBuilder(){role = new Role();}

    public RoleBuilder setId(int id){
        role.setId(id);
        return this;
    }

    public RoleBuilder setName(String roleName){
        role.setName(roleName);
        return this;
    }

    public Role build() {
        return role;
    }
}
