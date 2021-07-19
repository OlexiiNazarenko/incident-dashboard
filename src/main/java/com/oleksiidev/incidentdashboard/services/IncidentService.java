package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
import com.oleksiidev.incidentdashboard.model.Incident;

import java.util.List;

public interface IncidentService {

    Incident getIncidentById(Long id);

    List<Incident> getIncidentsByStatus(String statusName);

    List<Incident> getIncidentsByPlatformId(Long platformId);

    List<Incident> getIncidentsByServiceId(Long serviceId);

    List<Incident> getIncidentsByComponentId(Long componentId);

    Incident createIncident(CreateIncidentDTO createIncidentDTO);

    Incident updateIncident(Long id, CreateIncidentDTO createIncidentDTO);

    void deleteIncident(Long id);
}
