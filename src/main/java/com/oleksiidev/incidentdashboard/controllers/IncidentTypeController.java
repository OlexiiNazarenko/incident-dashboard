package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.services.IncidentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/api/incident_type")
public class IncidentTypeController {

    private final IncidentTypeService incidentTypeService;

    @GetMapping ("/all")
    public List<IncidentType> getAllIncidentTypes() {
        return incidentTypeService.getAllIncidentTypes();
    }

    @GetMapping("/id/{id}")
    public IncidentType getIncidentTypeById(@PathVariable @NonNull Long id) {
        return incidentTypeService.getIncidentTypeById(id);
    }

    @PostMapping ("/create")
    public IncidentType createIncidentType(
            @RequestBody String name) {
        return incidentTypeService.createIncidentType(name);
    }

    @PutMapping ("/update/id/{id}")
    public IncidentType updateIncidentTypeName(@PathVariable @NonNull Long id,
                                     @RequestBody String name) {
        return incidentTypeService.updateIncidentTypeName(id, name);
    }

    @DeleteMapping ("/delete/id/{id}")
    public void deleteIncidentType(@PathVariable @NonNull Long id) {
        incidentTypeService.deleteIncidentType(id);
    }
}

