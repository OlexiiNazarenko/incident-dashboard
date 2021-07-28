package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) return false;
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User createUser(UserDTO userDTO) {
        // TODO: add check for role permission; move to service
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setRole(Role.valueOf(userDTO.getRoleName()));
        newUser.setEmail(userDTO.getEmail());
        // TODO: send this password to user's email
        String password = RandomStringUtils.randomAlphanumeric(10);
        newUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    public User registerUser(RegistrationDTO registrationDTO) {
        User newUser = new User();
        newUser.setUsername(registrationDTO.getUsername());
        // TODO: do we want to set ADMIN role to this users by default?
        newUser.setRole(Role.ROLE_ADMIN);
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        return userRepository.save(newUser);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        // TODO: add check for role permission; move to service
        User user = userRepository.findUserById(id);
        user.setUsername(userDTO.getUsername());
        user.setRole(Role.valueOf(userDTO.getRoleName()));
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, String password) {
        User user = userRepository.findUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        // TODO: add check for role permission; move to service
        userRepository.deleteById(id);
    }
}
