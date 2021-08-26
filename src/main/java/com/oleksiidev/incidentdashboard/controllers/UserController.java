package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.auth.CurrentUser;
import com.oleksiidev.incidentdashboard.auth.UserPrincipal;
import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.exceptions.ResourceNotFoundException;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findUserById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping ("/id/{id}")
    public User getUserById(@PathVariable @NonNull Long id) {
        return userService.findUserById(id)
                .orElseThrow(() -> new NotFoundException("No user was found for id " + id ));
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestBody String email) {
        return userService.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find User with email: " + email));
    }

    @PostMapping ("/create")
    @PreAuthorize ("hasRole('ROLE_MANAGER')")
    public User createUser( @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping ("/update/id/{userId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public User updateUserById(@PathVariable Long userId,
                               @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping ("/delete/id/{userId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
