package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends CrudRepository<Incident, Long> {

    Incident findIncidentById(Long id);

    Incident findIncidentByType(IncidentType type);

    List<Incident> findIncidentsByStatus(IncidentStatus status);

    List<Incident> findIncidentsByComponent_Id(Long id);
}
