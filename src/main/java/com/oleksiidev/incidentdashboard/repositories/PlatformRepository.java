package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Platform;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findById(@NonNull Long id);
}
