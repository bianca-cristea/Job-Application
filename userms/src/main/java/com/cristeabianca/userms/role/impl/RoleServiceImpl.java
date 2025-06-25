package com.cristeabianca.userms.role.impl;

import com.cristeabianca.userms.role.Role;

import com.cristeabianca.userms.role.RoleRepository;
import com.cristeabianca.userms.role.RoleService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createRole(Role role) {
        try {
            roleRepository.save(role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public boolean updateRole(Long id) {

        if(roleRepository.existsById(id)){
            roleRepository.deleteById(id);
            return true;}
        return false;

    }
    @Override
    public boolean deleteRole(Long id) {

            if(roleRepository.existsById(id)){
            roleRepository.deleteById(id);
            return true;}
            return false;

    }
}
