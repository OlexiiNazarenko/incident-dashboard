package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
