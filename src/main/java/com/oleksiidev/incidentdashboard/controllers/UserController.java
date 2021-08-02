package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
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

    @GetMapping ("/id/{id}")
    public User getUserById(@PathVariable @NonNull Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestBody String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping ("/create")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping ("/update/id/{userId}")
    public User updateUserById(@PathVariable Long userId,
                               @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping ("/delete/id/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
