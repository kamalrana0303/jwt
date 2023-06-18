package com.root.project.role.service;

import com.root.project.role.Role;
import com.root.project.role.RoleName;

public interface RoleService {
    Role createRole(Role role);

    Role getRoleByName(RoleName name);

    Role updateRole(Role role);
}
