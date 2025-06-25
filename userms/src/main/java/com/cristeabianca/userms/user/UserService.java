package com.cristeabianca.userms.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);
    User getUserById(Long id);
    boolean createUser(User user);
    boolean updateUser(Long id, User user);
    boolean deleteUser(Long id);
    User save(User user);
}
