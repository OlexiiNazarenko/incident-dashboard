package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    Optional<Incident> findById(Long id);

    List<Incident> findIncidentsByStatus(IncidentStatus status);
}
