package com.oleksiidev.incidentdashboard.services.impl;

import com.oleksiidev.incidentdashboard.dto.CreateUserDTO;
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
    public User createUser(CreateUserDTO createUserDTO) {
        User newUser = new User();
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setRole(Role.valueOf(createUserDTO.getRoleName()));
        newUser.setEmail(createUserDTO.getEmail());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, CreateUserDTO createUserDTO) throws NoSuchElementException {
        User user = userRepository.findById(id).get();
        user.setUsername(createUserDTO.getUsername());
        user.setRole(Role.valueOf(createUserDTO.getRoleName()));
        user.setEmail(createUserDTO.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
