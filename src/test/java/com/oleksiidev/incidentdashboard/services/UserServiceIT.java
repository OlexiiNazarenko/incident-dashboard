package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class UserServiceIT {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceIT(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testGetUserById() {
        User expected = createUserAndSaveToDatabase();

        User actual = userService.getUserById(expected.getId());

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllUsers() {
        User user1 = createUserAndSaveToDatabase();
        User user2 = createUserAndSaveToDatabase();

        List<User> actual = userService.getAllUsers();

        assertEquals(actual, Arrays.asList(user1, user2));
    }

    @Test
    void testGetUserByEmail() {
        User expected = createUserAndSaveToDatabase();

        User actual = userService.getUserByEmail(expected.getEmail())
                .orElseThrow(() -> new NotFoundException("Saved User was not found in database by email"));

        assertEquals(actual, expected);
    }

    @Test
    void testGetUserByUsername() {
        User expected = createUserAndSaveToDatabase();

        User actual = userService.getUserByUsername(expected.getUsername())
                .orElseThrow(() -> new NotFoundException("Saved User was not found in database by username"));

        assertEquals(actual, expected);
    }

    @Test
    void testAuthenticate() {
        String password = RandomStringUtils.randomAlphanumeric(12);
        User user = createUserAndSaveToDatabase(password);

        User actual = userService.authenticate(user.getUsername(), password);

        assertEquals(actual, user);
    }

    @Test
    void testCreateUser() {
        String username = RandomStringUtils.randomAlphabetic(12);
        String email = RandomStringUtils.randomAlphabetic(12) + "@email.ok";
        Role role = Role.ROLE_ADMIN;

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setRoleName(role.name());

        User actual = userService.createUser(userDTO);

        assertNotNull(actual);
        assertEquals(actual.getUsername(), username);
        assertEquals(actual.getEmail(), email);
        assertEquals(actual.getRole(), role);
        assertNotNull(actual.getPassword());
    }

    @Test
    void testRegisterUser() {
        String username = RandomStringUtils.randomAlphabetic(12);
        String email = RandomStringUtils.randomAlphabetic(12) + "@email.ok";
        String password = RandomStringUtils.randomAlphanumeric(12);

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUsername(username);
        registrationDTO.setEmail(email);
        registrationDTO.setPassword(password);

        User actual = userService.registerUser(registrationDTO);

        assertEquals(actual.getUsername(), username);
        assertEquals(actual.getEmail(), email);
        assertTrue(passwordEncoder.matches(password, actual.getPassword()));
    }

    @Test
    void testUpdateUser() {
        User user1 = createUserAndSaveToDatabase();
        User user2 = createUser();
        UserDTO userDTO = userToDTO(user2);

        User actual = userService.updateUser(user1.getId(), userDTO);

        assertEquals(actual.getId(), user1.getId());
        assertEquals(actual.getUsername(), user2.getUsername());
        assertEquals(actual.getEmail(), user2.getEmail());
        assertEquals(actual.getPassword(), user1.getPassword());
    }

    @Test
    void testUpdateUserPassword() {
        User user = createUserAndSaveToDatabase();
        String newPassword = RandomStringUtils.randomAlphanumeric(12);

        User actual = userService.updateUserPassword(user.getUsername(), newPassword);

        assertTrue(passwordEncoder.matches(newPassword, actual.getPassword()));
    }

    @Test
    void testDeleteUser() {
        User user = createUserAndSaveToDatabase();
        assertNotEquals(userRepository.findById(user.getId()), Optional.empty());

        userService.deleteUser(user.getId());

        assertEquals(userRepository.findById(user.getId()), Optional.empty());
    }

    private User createUser() {
        User user = new User();
        user.setUsername(RandomStringUtils.randomAlphabetic(12));
        user.setEmail(RandomStringUtils.randomAlphabetic(12) + "@email.ok");
        user.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(12)));
        user.setRole(Role.ROLE_ADMIN);
        return user;
    }

    private User createUserAndSaveToDatabase() {
        return userRepository.save(createUser());
    }

    private User createUserAndSaveToDatabase(String password) {
        User user = createUser();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    private UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleName(user.getRole().name());
        return userDTO;
    }
}