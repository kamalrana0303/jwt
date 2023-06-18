package com.root.project.role.repository;

import com.root.project.role.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByRoleName(String name);
}
