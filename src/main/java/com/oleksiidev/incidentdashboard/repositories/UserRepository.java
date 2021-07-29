package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    User findUserByEmail(String email);
}
