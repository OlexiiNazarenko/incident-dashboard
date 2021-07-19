package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.CreateIncidentDTO;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.services.IncidentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/incident")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    private final Logger logger = LoggerFactory.getLogger(IncidentController.class);

    @GetMapping("/id")
    public ResponseEntity getIncidentById(@PathVariable Long id) {
        try {
            Incident user = incidentService.getIncidentById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (NoSuchElementException nse) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("An error occurred during GET request to /id: ", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<Incident> createIncident(@RequestBody CreateIncidentDTO createIncidentDTO) {
        // TODO: add check for role permission
        return null;
    }

    @PutMapping ("/update")
    public ResponseEntity<Incident> updateIncidentById(@PathVariable Long incidentId, @RequestBody CreateIncidentDTO createIncidentDTO) {
        // TODO: add check for role permission
        return null;
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<Incident> deleteIncidentById(@PathVariable Long incidentId, @RequestBody CreateIncidentDTO createIncidentDTO) {
        // TODO: add check for role permission
        return null;
    }
}
