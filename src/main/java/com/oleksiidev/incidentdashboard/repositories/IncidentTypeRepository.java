package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentTypeRepository extends JpaRepository<IncidentType, Long> {

    IncidentType findIncidentTypeById(Long id);
}
