package com.cristeabianca.job_application.user.impl;

import com.cristeabianca.job_application.user.User;
import com.cristeabianca.job_application.user.UserRepository;
import com.cristeabianca.job_application.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    DataSource dataSource;

    @Override
    public boolean createUser(com.cristeabianca.job_application.user.User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }


    @Override
    public boolean updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existing = optionalUser.get();
            existing.setUsername(user.getUsername());
            if (!user.getPassword().isEmpty() && !passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
                existing.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            existing.setRoles(user.getRoles());
            userRepository.save(existing);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}