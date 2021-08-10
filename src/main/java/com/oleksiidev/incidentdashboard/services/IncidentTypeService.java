package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IncidentTypeService {

    private final IncidentTypeRepository incidentTypeRepository;

    public Optional<IncidentType> findIncidentTypeById(Long id) {
        return incidentTypeRepository.findById(id);
    }

    public List<IncidentType> getAllIncidentTypes() {
        return incidentTypeRepository.findAll();
    }

    public IncidentType createIncidentType(String name) {
        IncidentType newIncidentType = new IncidentType();
        newIncidentType.setName(name);
        return incidentTypeRepository.save(newIncidentType);
    }

    public IncidentType updateIncidentTypeName(Long id, String newName) {
        IncidentType incidentType = incidentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Incident Type", id));
        incidentType.setName(newName);
        return incidentTypeRepository.save(incidentType);
    }

    public void deleteIncidentType(Long id) {
        incidentTypeRepository.deleteById(id);
    }
}
