package com.oleksiidev.incidentdashboard.integration.services;

import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import com.oleksiidev.incidentdashboard.services.UserService;
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

        Optional<User> actual = userService.findUserById(expected.getId());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetAllUsers() {
        User user1 = createUserAndSaveToDatabase();
        User user2 = createUserAndSaveToDatabase();

        List<User> actual = userService.getAllUsers();

        assertEquals(Arrays.asList(user1, user2), actual);
    }

    @Test
    void testGetUserByEmail() {
        User expected = createUserAndSaveToDatabase();

        Optional<User> actual = userService.getUserByEmail(expected.getEmail());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetUserByUsername() {
        User expected = createUserAndSaveToDatabase();

        Optional<User> actual = userService.getUserByUsername(expected.getUsername());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testAuthenticate() {
        String password = RandomStringUtils.randomAlphanumeric(12);
        User user = createUserAndSaveToDatabase(password);

        User actual = userService.authenticate(user.getUsername(), password);

        assertEquals(user, actual);
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
        assertEquals(username, actual.getUsername());
        assertEquals(email, actual.getEmail());
        assertEquals(role, actual.getRole());
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

        assertEquals(username, actual.getUsername());
        assertEquals(email, actual.getEmail());
        assertTrue(passwordEncoder.matches(password, actual.getPassword()));
    }

    @Test
    void testUpdateUser() {
        User user1 = createUserAndSaveToDatabase();
        User user2 = createUser();
        UserDTO userDTO = userToDTO(user2);

        User actual = userService.updateUser(user1.getId(), userDTO);

        assertEquals(user1.getId(), actual.getId());
        assertEquals(user2.getUsername(), actual.getUsername());
        assertEquals(user2.getEmail(), actual.getEmail());
        assertEquals(user1.getPassword(), actual.getPassword());
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

        assertEquals(Optional.empty(), userRepository.findById(user.getId()));
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