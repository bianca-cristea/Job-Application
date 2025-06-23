package com.cristeabianca.job_application.role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    boolean createRole(Role role);
    boolean updateRole(Long id);
    boolean deleteRole(Long id);
}
