package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("No user was found for id " + id ));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new AuthenticationCredentialsNotFoundException("Password doesn't match");
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public User createUser(UserDTO userDTO) {
        // TODO: add check for role permission
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setRole(Role.valueOf(userDTO.getRoleName()));
        newUser.setEmail(userDTO.getEmail());
        // TODO: send this password to user's email
        String password = RandomStringUtils.randomAlphanumeric(10);
        newUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public User registerUser(RegistrationDTO registrationDTO) {
        User newUser = new User();
        newUser.setUsername(registrationDTO.getUsername());
        // TODO: do we want to set ADMIN role to this users by default?
        newUser.setRole(Role.ROLE_ADMIN);
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        return userRepository.save(newUser);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public User updateUser(Long id, UserDTO userDTO) {
        // TODO: add check for role permission
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("No user was found for id " + id ));
        user.setUsername(userDTO.getUsername());
        user.setRole(Role.valueOf(userDTO.getRoleName()));
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public User updateUserPassword(String username, String password) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user was found for username " + username ));
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void deleteUser(Long id) {
        // TODO: add check for role permission
        userRepository.deleteById(id);
    }
}
