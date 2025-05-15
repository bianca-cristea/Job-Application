package com.cristeabianca.job_application.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        boolean result = userService.createUser(user);
        return result ? new ResponseEntity<>("User created", HttpStatus.CREATED)
                : new ResponseEntity<>("Could not create user", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean result = userService.updateUser(id, user);
        return result ? new ResponseEntity<>("User updated", HttpStatus.OK)
                : new ResponseEntity<>("Could not update user", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean result = userService.deleteUser(id);
        return result ? new ResponseEntity<>("User deleted", HttpStatus.OK)
                : new ResponseEntity<>("Could not delete user", HttpStatus.NOT_FOUND);
    }
}
