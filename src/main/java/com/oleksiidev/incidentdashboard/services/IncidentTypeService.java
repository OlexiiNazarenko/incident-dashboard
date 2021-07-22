package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncidentTypeService {

    private final IncidentTypeRepository incidentTypeRepository;

    public IncidentType getIncidentTypeById(Long id) {
        return incidentTypeRepository.findIncidentTypeById(id);
    }

    public List<IncidentType> getAllIncidentTypes() {
        return (List<IncidentType>) incidentTypeRepository.findAll();
    }

    public IncidentType createIncidentType(String name) {
        IncidentType newIncidentType = new IncidentType();
        newIncidentType.setName(name);
        return incidentTypeRepository.save(newIncidentType);
    }

    public IncidentType updateIncidentTypeName(Long id, String newName) {
        IncidentType incidentType = incidentTypeRepository.findIncidentTypeById(id);
        incidentType.setName(newName);
        return incidentTypeRepository.save(incidentType);
    }

    public void deleteIncidentType(Long id) {
        incidentTypeRepository.deleteById(id);
    }
}
