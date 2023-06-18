package com.root.project.role.service;

import com.root.project.role.Role;
import com.root.project.role.RoleName;
import com.root.project.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role createRole(Role role){
        return this.roleRepository.save(role);
    }


    @Override
    public Role getRoleByName(RoleName name){
        return this.roleRepository.findRoleByRoleName(name.name());
    }
    @Override
    public Role updateRole(Role role){
        return this.roleRepository.save(role);
    }

    @PostConstruct
    public void createAllRoles(){
        Arrays.stream(RoleName.values()).forEach(roleName->{
            Role role = this.roleRepository.findRoleByRoleName(roleName.name());
            if(role==null){
                Role role1 = new Role(roleName.name(), new HashSet<>());
                this.roleRepository.save(role1);
            }
        });
    }
}
