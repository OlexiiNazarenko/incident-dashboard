package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
import com.oleksiidev.incidentdashboard.model.Incident;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/incident")
public class IncidentController {

    @GetMapping("/id")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/status")
    public ResponseEntity<List<Incident>> getIncidentsByStatus(@PathVariable String statusName) {
        return null;
    }

    @GetMapping("/platformId")
    public ResponseEntity<List<Incident>> getIncidentsByPlatformId(@PathVariable Long platformId) {
        return null;
    }

    @GetMapping("/serviceId")
    public ResponseEntity<List<Incident>> getIncidentsByServiceId(@PathVariable Long serviceId) {
        return null;
    }

    @GetMapping("/componentId")
    public ResponseEntity<List<Incident>> getIncidentsByComponentId(@PathVariable Long componentId) {
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<Incident> createIncident(@RequestBody IncidentDTO incidentDTO) {

        return null;
    }

    @PutMapping ("/update")
    public ResponseEntity<Incident> updateIncidentById(@PathVariable Long incidentId, @RequestBody IncidentDTO incidentDTO) {
        return null;
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<Incident> deleteIncidentById(@PathVariable Long incidentId, @RequestBody IncidentDTO incidentDTO) {
        return null;
    }
}
