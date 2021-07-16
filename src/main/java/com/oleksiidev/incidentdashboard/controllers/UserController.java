package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
import com.oleksiidev.incidentdashboard.dto.CreateUserDTO;
import com.oleksiidev.incidentdashboard.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/user")
public class UserController {


    @GetMapping ("/id")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestBody String email) {
        return null;
    }

    @PostMapping ("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        // TODO: add check for role permission
        return null;
    }

    @PutMapping ("/update")
    public ResponseEntity<User> updateUserById(@PathVariable Long userId, @RequestBody CreateIncidentDTO userDTO) {
        // TODO: add check for role permission
        return null;
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<User> deleteUserById(@PathVariable Long userId, @RequestBody CreateIncidentDTO userDTO) {
        // TODO: add check for role permission
        return null;
    }
}
