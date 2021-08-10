package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.services.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/incident")
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping("/id/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentService.findIncidentById(id)
                .orElseThrow(() -> new NotFoundException(Incident.class, id));
    }

    @GetMapping("/status/{statusName}")
    public List<Incident> getIncidentsByStatus(@PathVariable String statusName) {
        return incidentService.getIncidentsByStatus(statusName);
    }

    @GetMapping("/componentId/{componentId}")
    public List<Incident> getIncidentsByPlatformId(@PathVariable Long componentId) {
        return incidentService.getIncidentsByComponentId(componentId);
    }

    @PostMapping("/create")
    public Incident createIncident(@RequestBody IncidentDTO incidentDTO) {
        return incidentService.createIncident(incidentDTO);
    }

    @PutMapping ("/update/id/{incidentId}")
    public Incident updateIncidentById(@PathVariable Long incidentId, @RequestBody IncidentDTO incidentDTO) {
        return incidentService.updateIncident(incidentId, incidentDTO);
    }

    @DeleteMapping ("/delete/id/{incidentId}")
    public void deleteIncidentById(@PathVariable Long incidentId) {
        incidentService.deleteIncident(incidentId);
    }
}
