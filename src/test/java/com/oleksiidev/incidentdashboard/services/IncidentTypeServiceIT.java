package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class IncidentTypeServiceIT {

    private final IncidentTypeService incidentTypeService;
    private final IncidentTypeRepository incidentTypeRepository;

    @Autowired
    IncidentTypeServiceIT(IncidentTypeService incidentTypeService, IncidentTypeRepository incidentTypeRepository) {
        this.incidentTypeService = incidentTypeService;
        this.incidentTypeRepository = incidentTypeRepository;
    }

    @AfterEach
    void tearDown() {
        incidentTypeRepository.deleteAll();
    }

    @Test
    void testGetIncidentTypeById() {
        IncidentType expected = createIncidentTypeAndSaveToDatabase();

        Optional<IncidentType> actual = incidentTypeService.findIncidentTypeById(expected.getId());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetAllIncidentTypes() {
        IncidentType incidentType1 = createIncidentTypeAndSaveToDatabase();
        IncidentType incidentType2 = createIncidentTypeAndSaveToDatabase();

        List<IncidentType> expected = Arrays.asList(incidentType1, incidentType2);
        List<IncidentType> actual = incidentTypeService.getAllIncidentTypes();

        assertEquals(expected, actual);
    }

    @Test
    void testCreateIncidentType() {
        String name = "Incident Type";
        IncidentType actual = incidentTypeService.createIncidentType(name);

        assertEquals(name, actual.getName());
    }

    @Test
    void testUpdateIncidentTypeName() {
        IncidentType incidentType = createIncidentTypeAndSaveToDatabase();

        String newName = "New Incident Type Name";
        IncidentType actual = incidentTypeService.updateIncidentTypeName(incidentType.getId(), newName);

        assertEquals(newName, actual.getName());
    }

    @Test
    void testDeleteIncidentType() {
        IncidentType incidentType = createIncidentTypeAndSaveToDatabase();

        incidentTypeService.deleteIncidentType(incidentType.getId());

        assertEquals(Optional.empty(), incidentTypeRepository.findById(incidentType.getId()));
    }

    private IncidentType createIncidentType(String name) {
        IncidentType incidentType = new IncidentType();
        incidentType.setName(name);
        return incidentType;
    }

    private IncidentType createIncidentType() {
        String name = RandomStringUtils.randomAlphabetic(10);
        return createIncidentType(name);
    }

    private IncidentType createIncidentTypeAndSaveToDatabase(String name) {
        return incidentTypeRepository.save(createIncidentType(name));
    }

    private IncidentType createIncidentTypeAndSaveToDatabase() {
        return incidentTypeRepository.save(createIncidentType());
    }
}