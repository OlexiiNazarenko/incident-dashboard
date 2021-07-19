package com.oleksiidev.incidentdashboard.services.impl;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.repositories.IncidentRepository;
import com.oleksiidev.incidentdashboard.services.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id).get();
    }

    @Override
    public List<Incident> getIncidentsByStatus(String statusName) {
        return incidentRepository.findIncidentsByStatus(IncidentStatus.valueOf(statusName));
    }

    @Override
    public List<Incident> getIncidentsByPlatformId(Long platformId) {
        return null;
    }

    @Override
    public List<Incident> getIncidentsByServiceId(Long serviceId) {
        return null;
    }

    @Override
    public List<Incident> getIncidentsByComponentId(Long componentId) {
        return incidentRepository.findIncidentsByComponent_Id(componentId);
    }

    @Override
    public Incident createIncident(CreateIncidentDTO createIncidentDTO) {
        Incident newIncident = new Incident();
        return null;
    }

    @Override
    public Incident updateIncident(Long id, CreateIncidentDTO createIncidentDTO) {
        return null;
    }

    @Override
    public void deleteIncident(Long id) {

    }
}
