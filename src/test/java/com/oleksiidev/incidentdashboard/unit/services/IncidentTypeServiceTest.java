package com.oleksiidev.incidentdashboard.unit.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import com.oleksiidev.incidentdashboard.services.IncidentTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.gt;

@ExtendWith (MockitoExtension.class)
class IncidentTypeServiceTest {

    private final IncidentTypeService incidentTypeService;

    private final IncidentType incidentType1;
    private final IncidentType incidentType2;
    private final IncidentType incidentType3;
    private final IncidentType incidentType4;

    public IncidentTypeServiceTest(@Mock IncidentTypeRepository incidentTypeRepository) {
        incidentTypeService = new IncidentTypeService(incidentTypeRepository);

        incidentType1 = new IncidentType();
        incidentType1.setId(1L);
        incidentType1.setName("Incident Type 1");

        incidentType2 = new IncidentType();
        incidentType2.setId(2L);
        incidentType2.setName("Incident Type 2");

        IncidentType incidentTypeToSave = new IncidentType();
        incidentTypeToSave.setName("Incident Type 3");

        incidentType3 = new IncidentType();
        incidentType3.setId(3L);
        incidentType3.setName("Incident Type 3");

        incidentType4 = new IncidentType();
        incidentType4.setId(1L);
        incidentType4.setName("Incident Type 4");

        Mockito.when(incidentTypeRepository.findById(1L)).thenReturn(Optional.of(incidentType1));
        Mockito.when(incidentTypeRepository.findById(2L)).thenReturn(Optional.of(incidentType2));
        Mockito.when(incidentTypeRepository.findById(gt(2L))).thenReturn(Optional.empty());
        Mockito.when(incidentTypeRepository.findAll()).thenReturn(Arrays.asList(incidentType1, incidentType2));
        Mockito.when(incidentTypeRepository.save(incidentTypeToSave)).thenReturn(incidentType3);
        Mockito.when(incidentTypeRepository.save(incidentType4)).thenReturn(incidentType4);
    }

    @Test
    void testFindIncidentTypeById_Success() {
        Optional<IncidentType> actual = incidentTypeService.findIncidentTypeById(1L);

        assertTrue(actual.isPresent());
        assertEquals(incidentType1, actual.get());
    }

    @Test
    void testFindIncidentTypeById_WrongIncidentTypeId() {
        Optional<IncidentType> actual = incidentTypeService.findIncidentTypeById(999L);

        assertFalse(actual.isPresent());
    }

    @Test
    void testGetAllIncidentTypes() {
        List<IncidentType> actual = incidentTypeService.getAllIncidentTypes();

        assertEquals(Arrays.asList(incidentType1, incidentType2), actual);
    }

    @Test
    void testCreateIncidentType() {
        IncidentType actual = incidentTypeService.createIncidentType("Incident Type 3");

        assertEquals(incidentType3, actual);
    }

    @Test
    void testUpdateIncidentTypeName_Success() {
        IncidentType actual = incidentTypeService.updateIncidentTypeName(1L, "Incident Type 4");

        assertEquals(incidentType4, actual);
    }

    @Test
    void testUpdateIncidentTypeName_WrongId() {
        assertThrows(NotFoundException.class, () -> incidentTypeService.updateIncidentTypeName(911L, "Incident Type 3"));
    }
}