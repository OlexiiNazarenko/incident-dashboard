package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class IncidentTypeServiceTestIT {

    @Autowired
    private final IncidentTypeService incidentTypeService;

    @Autowired
    private final IncidentTypeRepository incidentTypeRepository;

    @Autowired
    IncidentTypeServiceTestIT(IncidentTypeService incidentTypeService, IncidentTypeRepository incidentTypeRepository) {
        this.incidentTypeService = incidentTypeService;
        this.incidentTypeRepository = incidentTypeRepository;
    }

    @AfterEach
    void tearDown() {
        incidentTypeRepository.deleteAll();
    }

    @Test
    void testGetIncidentTypeById() {
        IncidentType expected = new IncidentType();
        expected.setName("Random Name");

        expected = incidentTypeRepository.save(expected);
        IncidentType actual = incidentTypeService.getIncidentTypeById(expected.getId())
                .orElseThrow(() -> new NotFoundException("Saved Incident Type was not found in database by its id."));

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllIncidentTypes() {
        IncidentType incidentType1 = new IncidentType();
        incidentType1.setName("Test Incident Type");

        IncidentType incidentType2 = new IncidentType();
        incidentType2.setName("Another Test Incident Type");

        List<IncidentType> expected = incidentTypeRepository.saveAll(Arrays.asList(incidentType1, incidentType2));
        List<IncidentType> actual = incidentTypeService.getAllIncidentTypes();

        assertEquals(actual, expected);
    }

    @Test
    void testCreateIncidentType() {
        String name = "Incident Type";
        IncidentType actual = incidentTypeService.createIncidentType(name);

        assertEquals(actual.getName(), name);
    }

    @Test
    void testUpdateIncidentTypeName() {
        String name = "Incident Type _1";
        IncidentType incidentType = new IncidentType();
        incidentType.setName(name);
        Long savedId = incidentTypeRepository.save(incidentType).getId();

        String newName = "New Incident Type Name";
        IncidentType actual = incidentTypeService.updateIncidentTypeName(savedId, newName);

        assertEquals(actual.getName(), newName);
    }

    @Test
    void testDeleteIncidentType() {
        String name = "Incident Type To Delete";
        IncidentType incidentType = new IncidentType();
        incidentType.setName(name);
        Long savedId = incidentTypeRepository.save(incidentType).getId();

        incidentTypeService.deleteIncidentType(savedId);

        IncidentType actual = incidentTypeService.getIncidentTypeById(savedId)
                .orElseThrow(() -> new NotFoundException("Saved Incident Type was not found in database by its id."));

        assertNull(actual);
    }
}