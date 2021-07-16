package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.UserDTO;
import com.oleksiidev.incidentdashboard.model.User;

public interface UserService {

    User getUserById(Long id);

    User createUser(UserDTO userDTO);

    User updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}
