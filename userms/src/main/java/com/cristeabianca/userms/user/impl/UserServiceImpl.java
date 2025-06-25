package com.cristeabianca.userms.user.impl;

import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import com.cristeabianca.job_application.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    private DataSource dataSource;



    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        logger.debug("Fetching all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) {
        logger.debug("Fetching user by id: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createUser(User user) {
        if (user == null) {
            logger.warn("Attempted to create null user");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            logger.warn("Attempted to create user with null or empty password");
            return false;
        }

        logger.info("Creating user with username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.debug("User {} created successfully", user.getUsername());
        return true;
    }
    @Override
    public boolean updateUser(Long id, User user) {
        logger.info("Updating user with id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existing = optionalUser.get();
            existing.setUsername(user.getUsername());
            if (!user.getPassword().isEmpty() && !passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
                existing.setPassword(passwordEncoder.encode(user.getPassword()));
                logger.debug("Password updated for user id: {}", id);
            }
            existing.setRoles(user.getRoles());
            userRepository.save(existing);
            logger.info("User with id {} updated successfully", id);
            return true;
        } else {
            logger.warn("User with id {} not found for update", id);
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        try {
            userRepository.deleteById(id);
            logger.info("User with id {} deleted successfully", id);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete user with id {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public User save(User user) {
        logger.debug("Saving user: {}", user.getUsername());
        return userRepository.save(user);
    }
}
