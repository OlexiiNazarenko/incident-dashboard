package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    private final UserService userRepository;
    private final IncidentTypeService incidentTypeService;
    private final ComponentService componentService;


    public Optional<Incident> findIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    public List<Incident> getIncidentsByStatus(String statusName) {
        return incidentRepository.findIncidentsByStatus(IncidentStatus.valueOf(statusName));
    }

    public List<Incident> getIncidentsByComponentId(Long componentId) {
        return incidentRepository.findIncidentsByComponent_Id(componentId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Incident createIncident(IncidentDTO incidentDTO) {
        Incident newIncident = new Incident();
        return saveIncident(incidentDTO, newIncident);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Incident updateIncident(Long id, IncidentDTO incidentDTO) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Incident found for id " + id));
        return saveIncident(incidentDTO, incident);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }

    private Incident saveIncident(IncidentDTO incidentDTO, Incident newIncident) {
        newIncident.setCreator(userRepository.findUserById(incidentDTO.getUserID())
                .orElseThrow(() -> new NotFoundException("No user for id " + incidentDTO.getUserID())));
        newIncident.setDescription(incidentDTO.getDescription());
        newIncident.setType(incidentTypeService.findIncidentTypeById(incidentDTO.getIncidentTypeId())
                .orElseThrow(() -> new NotFoundException(IncidentType.class, incidentDTO.getIncidentTypeId())));
        newIncident.setComponent(componentService.findComponentById(incidentDTO.getComponentId())
                .orElseThrow(() -> new NotFoundException(Component.class, incidentDTO.getComponentId())));
        newIncident.setStatus(IncidentStatus.valueOf(incidentDTO.getStatus()));
        newIncident.setStartDate(incidentDTO.getStartDate());
        newIncident.setEndDate(incidentDTO.getEndDate());
        return incidentRepository.save(newIncident);
    }
}
