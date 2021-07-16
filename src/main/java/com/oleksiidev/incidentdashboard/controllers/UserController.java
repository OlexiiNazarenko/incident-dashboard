package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
import com.oleksiidev.incidentdashboard.dto.CreateUserDTO;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;


@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping ("/id")
    public ResponseEntity getUserById(@PathVariable @NonNull Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (NoSuchElementException nse) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred during GET request to /id: ", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email")
    public ResponseEntity getUserByEmail(@RequestBody String email) {
        try {
            User user = userService.getUserByEmail(email);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (NoSuchElementException nse) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred during GET request to /email", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/create")
    public ResponseEntity createUser(@RequestBody CreateUserDTO createUserDTO) {
        // TODO: add check for role permission
        try {
            User user = userService.createUser(createUserDTO);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during POST request to /create: ", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping ("/update")
    public ResponseEntity updateUserById(@PathVariable Long userId, @RequestBody CreateUserDTO createUserDTO) {
        // TODO: add check for role permission
        try {
            User user = userService.updateUser(userId, createUserDTO);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during PUT request to /update: ", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping ("/delete")
    public ResponseEntity deleteUserById(@PathVariable Long userId) {
        // TODO: add check for role permission
        try {
            userService.deleteUser(userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during DELETE request to /update: ", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
