package com.oleksiidev.incidentdashboard.unit.services;

import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import com.oleksiidev.incidentdashboard.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (MockitoExtension.class)
class UserServiceTest {
    private static final String USER_1_EMAIL = "user1@email.co";
    private static final String USER_1_USERNAME = "User1";
    private static final String USER_1_PASSWORD = "Password&1";
    private static final String USER_2_EMAIL = "user2@email.co";
    private static final String USER_2_USERNAME = "User2";
    private static final Role USER_2_ROLE = Role.ROLE_ADMIN;
    private static final String USER_2_NEW_PASSWORD = "User2NewP@ssword";
    private static final String UPD_USER_2_EMAIL = "user_new@email.co";
    private static final String UPD_USER_2_USERNAME = "User New Name";
    private static final Role UPD_USER_2_ROLE = Role.ROLE_MANAGER;
    private static final String USER_4_EMAIL = "user4@email.co";
    private static final String USER_4_USERNAME = "User4";
    private static final String USER_4_PASSWORD = "Password&4";

    private final UserService userService;
    private final User user1;
    private final User user2;
    private final User user3;
    private final User user4;
    private final User user2WithPassword;

    public UserServiceTest(@Mock UserRepository userRepository) {
        userService = new UserService(userRepository);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user1 = new User();
        user1.setId(1L);
        user1.setUsername(USER_1_USERNAME);
        user1.setRole(Role.ROLE_ADMIN);
        user1.setEmail(USER_1_EMAIL);
        user1.setPassword(passwordEncoder.encode(USER_1_PASSWORD));

        User userToSave = new User();
        userToSave.setUsername("User2");
        userToSave.setEmail("user2@email.co");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername(USER_2_USERNAME);
        user2.setEmail(USER_2_EMAIL);
        user2.setRole(USER_2_ROLE);

        user2WithPassword = new User();
        user2WithPassword.setId(2L);
        user2WithPassword.setUsername(USER_2_USERNAME);
        user2WithPassword.setEmail(USER_2_EMAIL);
        user2WithPassword.setRole(USER_2_ROLE);
        user2WithPassword.setPassword(passwordEncoder.encode(USER_2_NEW_PASSWORD));

        user3 = new User();
        user3.setId(2L);
        user3.setUsername(UPD_USER_2_USERNAME);
        user3.setEmail(UPD_USER_2_EMAIL);
        user3.setRole(UPD_USER_2_ROLE);

        String encodedPassword4 = passwordEncoder.encode(USER_4_PASSWORD);

        User user4ToSave = new User();
        user4ToSave.setUsername(USER_4_USERNAME);
        user4ToSave.setRole(Role.ROLE_ADMIN);
        user4ToSave.setEmail(USER_4_EMAIL);
        user4ToSave.setPassword(encodedPassword4);

        user4 = new User();
        user4.setId(4L);
        user4.setUsername(USER_4_USERNAME);
        user4.setRole(Role.ROLE_ADMIN);
        user4.setEmail(USER_4_EMAIL);
        user4.setPassword(encodedPassword4);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        Mockito.when(userRepository.findUserByEmail(USER_1_EMAIL)).thenReturn(Optional.of(user1));

        Mockito.when(userRepository.findUserByUsername(USER_1_USERNAME)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findUserByUsername(USER_2_USERNAME)).thenReturn(Optional.of(user2));

        Mockito.when(userRepository.save(userToSave)).thenReturn(user2);
        Mockito.when(userRepository.save(user4ToSave)).thenReturn(user4);
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        Mockito.when(userRepository.save(user2WithPassword)).thenReturn(user2WithPassword);
    }

    @Test
    void testGetUserById_Success() {
        Optional<User> actual = userService.findUserById(1L);
        assertTrue(actual.isPresent());
        assertEquals(user1, actual.get());
    }

    @Test
    void testGetUserById_ThrowNotFoundExceptionForInappropriateId() {
        assertFalse(userService.findUserById(990L).isPresent());
    }

    @Test
    void testGetUserByEmail_Success() {
        Optional<User> actual = userService.findUserByEmail(USER_1_EMAIL);
        assertTrue(actual.isPresent());
        assertEquals(user1, actual.get());
    }

    @Test
    void testGetUserByEmail_ReturnNullForInappropriateEmail() {
        Optional<User> actual = userService.findUserByEmail("blabla@email.co");
        assertFalse(actual.isPresent());
    }

    @Test
    void getUserByUsername_Success() {
        Optional<User> actual = userService.findUserByUsername(USER_1_USERNAME);
        assertTrue(actual.isPresent());
        assertEquals(user1, actual.get());
    }

    @Test
    void getUserByUsername_ReturnNullForInappropriateUsername() {
        Optional<User> actual = userService.findUserByUsername("blablaUsername");
        assertFalse(actual.isPresent());
    }

    @Test
    void testUpdateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRoleName(UPD_USER_2_ROLE.name());
        userDTO.setUsername(UPD_USER_2_USERNAME);
        userDTO.setEmail(UPD_USER_2_EMAIL);

        User actual = userService.updateUser(2L, userDTO);

        assertEquals(user3, actual);
    }

    @Test
    void testUpdateUser_ThrowNotFoundExceptionForInappropriateId() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRoleName(UPD_USER_2_ROLE.name());
        userDTO.setUsername(UPD_USER_2_USERNAME);
        userDTO.setEmail(UPD_USER_2_EMAIL);

        assertThrows(NotFoundException.class, () -> userService.updateUser(729L, userDTO));
    }

    @Test
    void testUpdateUserPassword_ThrowNotFoundExceptionForInappropriateUsername() {
        assertThrows(NotFoundException.class, () -> userService.updateUserPassword("BlaBlaUserName", USER_2_NEW_PASSWORD));
    }
}