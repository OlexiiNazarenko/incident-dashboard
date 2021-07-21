package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
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
@RequestMapping("/incident")
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping("/id/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    @GetMapping("/status/{statusName}")
    public List<Incident> getIncidentsByStatus(@PathVariable String statusName) {
        return incidentService.getIncidentsByStatus(statusName);
    }

    @GetMapping("/platformId/{platformId}")
    public List<Incident> getIncidentsByPlatformId(@PathVariable Long platformId) {
        return incidentService.getIncidentsByPlatformId(platformId);
    }

    @GetMapping("/serviceId/{serviceId}")
    public List<Incident> getIncidentsByServiceId(@PathVariable Long serviceId) {
        return incidentService.getIncidentsByServiceId(serviceId);
    }

    @GetMapping("/componentId/{componentId}")
    public List<Incident> getIncidentsByComponentId(@PathVariable Long componentId) {
        return incidentService.getIncidentsByComponentId(componentId);
    }

    @PostMapping("/create")
    public Incident createIncident(@RequestBody CreateIncidentDTO createIncidentDTO) {
        return incidentService.createIncident(createIncidentDTO);
    }

    @PutMapping ("/update/id/{incidentId}")
    public Incident updateIncidentById(@PathVariable Long incidentId, @RequestBody CreateIncidentDTO createIncidentDTO) {
        return incidentService.updateIncident(incidentId, createIncidentDTO);
    }

    @DeleteMapping ("/delete/id/{incidentId}")
    public void deleteIncidentById(@PathVariable Long incidentId) {
        incidentService.deleteIncident(incidentId);
    }
}
