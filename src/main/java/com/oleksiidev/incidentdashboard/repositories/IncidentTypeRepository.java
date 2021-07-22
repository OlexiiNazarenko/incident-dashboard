package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.IncidentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentTypeRepository extends CrudRepository<IncidentType, Long> {

    IncidentType findIncidentTypeById(Long id);
}
