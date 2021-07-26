package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.repositories.IncidentRepository;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final IncidentTypeRepository incidentTypeRepository;


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

    public Incident createIncident(IncidentDTO incidentDTO) {
        // TODO: add check for role permission
        Incident newIncident = new Incident();
        newIncident.setCreator(userRepository.findUserById(incidentDTO.getUserID()));
        newIncident.setDescription(incidentDTO.getDescription());
        newIncident.setType(incidentTypeRepository.findIncidentTypeById(incidentDTO.getIncidentTypeId()));
        newIncident.setStatus(IncidentStatus.valueOf(incidentDTO.getStatus()));
        newIncident.setStartDate(incidentDTO.getStartDate());
        newIncident.setEndDate(incidentDTO.getEndDate());
        return incidentRepository.save(newIncident);
    }

    public Incident updateIncident(Long id, IncidentDTO incidentDTO) {
        // TODO: add check for role permission
        Incident incident = incidentRepository.findIncidentById(id);
        incident.setCreator(userRepository.findUserById(incidentDTO.getUserID()));
        incident.setDescription(incidentDTO.getDescription());
        incident.setType(incidentTypeRepository.findIncidentTypeById(incidentDTO.getIncidentTypeId()));
        incident.setStatus(IncidentStatus.valueOf(incidentDTO.getStatus()));
        incident.setStartDate(incidentDTO.getStartDate());
        incident.setEndDate(incidentDTO.getEndDate());
        return incidentRepository.save(incident);
    }

    public void deleteIncident(Long id) {
        // TODO: add check for role permission
        incidentRepository.deleteById(id);
    }
}
