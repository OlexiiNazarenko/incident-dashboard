package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.CreateUserDTO;
import com.oleksiidev.incidentdashboard.model.User;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    User createUser(CreateUserDTO createUserDTO);

    User updateUser(Long id, CreateUserDTO createUserDTO);

    void deleteUser(Long id);
}
