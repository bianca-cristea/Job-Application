package com.cristeabianca.userms.role;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return role != null ? new ResponseEntity<>(role, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody @Valid Role role) {
        boolean created = roleService.createRole(role);
        return created ? new ResponseEntity<>("Role created", HttpStatus.CREATED)
                : new ResponseEntity<>("Could not create role", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable Long id) {
        boolean updated = roleService.updateRole(id);
        return updated ? new ResponseEntity<>("Role updated", HttpStatus.OK)
                : new ResponseEntity<>("Could not update role", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        boolean deleted = roleService.deleteRole(id);
        return deleted ? new ResponseEntity<>("Role deleted", HttpStatus.OK)
                : new ResponseEntity<>("Could not delete role", HttpStatus.NOT_FOUND);
    }
}
