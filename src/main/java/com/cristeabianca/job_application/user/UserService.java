package com.cristeabianca.job_application.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    boolean createUser(User user);
    boolean updateUser(Long id, User user);
    boolean deleteUser(Long id);
}
