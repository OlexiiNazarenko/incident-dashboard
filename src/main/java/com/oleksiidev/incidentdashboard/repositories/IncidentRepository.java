package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    List<Incident> findIncidentsByStatus(IncidentStatus status);

    List<Incident> findIncidentsByComponent_Id(Long id);
}
