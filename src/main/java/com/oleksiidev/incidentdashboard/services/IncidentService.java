package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public Incident getIncidentById(Long id) {
        return incidentRepository.findIncidentById(id);
    }

    public List<Incident> getIncidentsByStatus(String statusName) {
        return incidentRepository.findIncidentsByStatus(IncidentStatus.valueOf(statusName));
    }

    public List<Incident> getIncidentsByPlatformId(Long platformId) {
        return null;
    }

    public List<Incident> getIncidentsByServiceId(Long serviceId) {
        return null;
    }

    public List<Incident> getIncidentsByComponentId(Long componentId) {
        return incidentRepository.findIncidentsByComponent_Id(componentId);
    }

    public Incident createIncident(CreateIncidentDTO createIncidentDTO) {
        // TODO: add check for role permission
        Incident newIncident = new Incident();
        return null;
    }

    public Incident updateIncident(Long id, CreateIncidentDTO createIncidentDTO) {
        // TODO: add check for role permission
        return null;
    }

    public void deleteIncident(Long id) {
        // TODO: add check for role permission
    }
}
