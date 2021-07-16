package com.oleksiidev.incidentdashboard.services.impl;

import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import com.oleksiidev.incidentdashboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) throws NoSuchElementException {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setRole(Role.valueOf(userDTO.getRoleName()));
        newUser.setEmail(userDTO.getEmail());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) throws NoSuchElementException {
        User user = userRepository.findById(id).get();
        user.setUsername(userDTO.getUsername());
        user.setRole(Role.valueOf(userDTO.getRoleName()));
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
