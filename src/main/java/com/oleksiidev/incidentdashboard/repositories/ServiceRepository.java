package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findById(Long id);

    List<Service> findServicesByPlatform(Platform platform);
}
