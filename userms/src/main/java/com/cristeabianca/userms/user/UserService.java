package user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<com.cristeabianca.userms.user.User> getAllUsers(Pageable pageable);
    com.cristeabianca.userms.user.User getUserById(Long id);
    boolean createUser(com.cristeabianca.userms.user.User user);
    boolean updateUser(Long id, com.cristeabianca.userms.user.User user);
    boolean deleteUser(Long id);
    com.cristeabianca.userms.user.User save(com.cristeabianca.userms.user.User user);
}
