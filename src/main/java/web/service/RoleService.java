package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> allRoles();

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);

    Role getRoleById(Long id);

    Role getRoleByName(String role);

}
