package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> findById(Long id);

    List<Component> findComponentsByService(Service service);
}
