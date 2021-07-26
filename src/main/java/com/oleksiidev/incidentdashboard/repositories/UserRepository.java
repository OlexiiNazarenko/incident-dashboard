package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(long id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
