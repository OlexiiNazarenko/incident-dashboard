package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.CreateUserDTO;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User createUser(CreateUserDTO createUserDTO) {
        // TODO: add check for role permission; move to service
        User newUser = new User();
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setRole(Role.valueOf(createUserDTO.getRoleName()));
        newUser.setEmail(createUserDTO.getEmail());
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, CreateUserDTO createUserDTO) {
        // TODO: add check for role permission; move to service
        User user = userRepository.findUserById(id);
        user.setUsername(createUserDTO.getUsername());
        user.setRole(Role.valueOf(createUserDTO.getRoleName()));
        user.setEmail(createUserDTO.getEmail());
        // TODO: Generate default password and send an email to change password to the user
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        // TODO: add check for role permission; move to service
        userRepository.deleteById(id);
    }
}
