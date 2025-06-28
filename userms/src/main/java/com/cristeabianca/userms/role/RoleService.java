package role;

import java.util.List;

public interface RoleService {
    List<com.cristeabianca.userms.role.Role> getAllRoles();
    com.cristeabianca.userms.role.Role getRoleById(Long id);
    boolean createRole(Role role);
    boolean updateRole(Long id, Role role);
    boolean deleteRole(Long id);
}
