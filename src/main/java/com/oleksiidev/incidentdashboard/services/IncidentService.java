package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentRepository;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final IncidentTypeRepository incidentTypeRepository;


    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
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
        return saveIncident(incidentDTO, newIncident);
    }

    public Incident updateIncident(Long id, IncidentDTO incidentDTO) {
        // TODO: add check for role permission
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Incident found for id " + id));
        return saveIncident(incidentDTO, incident);
    }

    public void deleteIncident(Long id) {
        // TODO: add check for role permission
        incidentRepository.deleteById(id);
    }

    private Incident saveIncident(IncidentDTO incidentDTO, Incident newIncident) {
        newIncident.setCreator(userRepository.findById(incidentDTO.getUserID())
                .orElseThrow(() -> new NotFoundException("No user for id " + incidentDTO.getUserID())));
        newIncident.setDescription(incidentDTO.getDescription());
        newIncident.setType(incidentTypeRepository.findById(incidentDTO.getIncidentTypeId())
                .orElseThrow(() -> new NotFoundException(IncidentType.class, incidentDTO.getIncidentTypeId())));
        newIncident.setStatus(IncidentStatus.valueOf(incidentDTO.getStatus()));
        newIncident.setStartDate(incidentDTO.getStartDate());
        newIncident.setEndDate(incidentDTO.getEndDate());
        return incidentRepository.save(newIncident);
    }
}
