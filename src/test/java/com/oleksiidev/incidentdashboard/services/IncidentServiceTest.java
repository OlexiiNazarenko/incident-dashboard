package com.oleksiidev.incidentdashboard.services;

import com.google.common.collect.Sets;
import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Incident;
import com.oleksiidev.incidentdashboard.model.IncidentStatus;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Role;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.repositories.ComponentRepository;
import com.oleksiidev.incidentdashboard.repositories.IncidentRepository;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.gt;

@ExtendWith (MockitoExtension.class)
class IncidentServiceTest {

    private final IncidentService incidentService;

    private final Incident incident1;
    private final Incident incident2;
    private final Incident incident3;
    private final Incident incident4;
    private final Date startDate = new Date(System.currentTimeMillis());
    private final Date endDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

    public IncidentServiceTest(@Mock IncidentRepository incidentRepository,
                               @Mock UserService userService,
                               @Mock IncidentTypeService incidentTypeService,
                               @Mock ComponentService componentService) {
        incidentService = new IncidentService(incidentRepository, userService, incidentTypeService, componentService);

        User creator1 = new User();
        creator1.setId(1L);
        creator1.setUsername("Username");
        creator1.setRole(Role.ROLE_ADMIN);
        creator1.setEmail("username@email.oc");

        Region region1 = new Region();
        region1.setId(1L);
        region1.setName("Region 1");

        Platform platform1 = new Platform();
        platform1.setId(1L);
        platform1.setName("Platform 1");

        Service service1 = new Service();
        service1.setId(1L);
        service1.setName("Service 1");
        service1.setPlatform(platform1);

        Component component = new Component();
        component.setId(1L);
        component.setName("Component 1");
        component.setService(service1);
        component.setRegions(Sets.newHashSet(region1));

        IncidentType incidentType = new IncidentType();
        incidentType.setId(1L);
        incidentType.setName("Incident Type 1");

        incident1 = new Incident();
        incident1.setId(1L);
        incident1.setType(incidentType);
        incident1.setStatus(IncidentStatus.OPEN);
        incident1.setDescription("Description goes here");
        incident1.setCreator(creator1);
        incident1.setComponent(component);
        incident1.setStartDate(startDate);
        incident1.setEndDate(endDate);

        incident2 = new Incident();
        incident2.setId(2L);
        incident2.setType(incidentType);
        incident2.setStatus(IncidentStatus.OPEN);
        incident2.setDescription("Description goes here");
        incident2.setCreator(creator1);
        incident2.setComponent(component);
        incident2.setStartDate(startDate);
        incident2.setEndDate(endDate);

        incident3 = new Incident();
        incident3.setId(3L);
        incident3.setType(incidentType);
        incident3.setStatus(IncidentStatus.OPEN);
        incident3.setDescription("Description goes here");
        incident3.setCreator(creator1);
        incident3.setComponent(component);
        incident3.setStartDate(startDate);
        incident3.setEndDate(endDate);

        Incident incidentToSave = new Incident();
        incidentToSave.setType(incidentType);
        incidentToSave.setStatus(IncidentStatus.OPEN);
        incidentToSave.setDescription("Description goes here");
        incidentToSave.setCreator(creator1);
        incidentToSave.setComponent(component);
        incidentToSave.setStartDate(startDate);
        incidentToSave.setEndDate(endDate);

        incident4 = new Incident();
        incident4.setId(3L);
        incident4.setType(incidentType);
        incident4.setStatus(IncidentStatus.CLOSED);
        incident4.setDescription("Description goes here");
        incident4.setCreator(creator1);
        incident4.setComponent(component);
        incident4.setStartDate(startDate);
        incident4.setEndDate(endDate);

        Mockito.when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident1));
        Mockito.when(incidentRepository.findById(2L)).thenReturn(Optional.of(incident2));
        Mockito.when(incidentRepository.findById(3L)).thenReturn(Optional.of(incident3));
        Mockito.when(incidentRepository.findById(gt(4L))).thenReturn(Optional.empty());
        Mockito.when(incidentRepository.findIncidentsByStatus(IncidentStatus.OPEN)).thenReturn(Arrays.asList(incident1, incident2));
        Mockito.when(incidentRepository.findIncidentsByComponent_Id(1L)).thenReturn(Arrays.asList(incident1, incident2));
        Mockito.when(incidentRepository.save(incidentToSave)).thenReturn(incident3);
        Mockito.when(incidentRepository.save(incident4)).thenReturn(incident4);

        Mockito.when(incidentTypeService.findIncidentTypeById(1L)).thenReturn(Optional.of(incidentType));
        Mockito.when(incidentTypeService.findIncidentTypeById(gt(1L))).thenReturn(Optional.empty());

        Mockito.when(userService.findUserById(1L)).thenReturn(Optional.of(creator1));
        Mockito.when(userService.findUserById(gt(1L))).thenReturn(Optional.empty());

        Mockito.when(componentService.findComponentById(1L)).thenReturn(Optional.of(component));
        Mockito.when(componentService.findComponentById(gt(1L))).thenReturn(Optional.empty());
    }

    @Test
    void testGetIncidentById_Success() {
        Optional<Incident> actual = incidentService.findIncidentById(1L);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), incident1);
    }

    @Test
    void testGetIncidentById_ReturnNullForWrongId() {
        Optional<Incident> actual = incidentService.findIncidentById(99L);
        assertFalse(actual.isPresent());
    }

    @Test
    void testGetIncidentsByStatus_Success() {
        List<Incident> actual = incidentService.getIncidentsByStatus(IncidentStatus.OPEN.name());
        assertEquals(Arrays.asList(incident1, incident2), actual);
    }

    @Test
    void testGetIncidentsByStatus_NoIncidentsByStatus() {
        List<Incident> actual = incidentService.getIncidentsByStatus(IncidentStatus.CLOSED.name());
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetIncidentsByStatus_WrongStatus() {
        assertThrows(RuntimeException.class, () -> incidentService.getIncidentsByStatus("WRONG_NAME"));
    }

    @Test
    void testGetIncidentsByComponentId_Success() {
        List<Incident> actual = incidentService.getIncidentsByComponentId(1L);
        assertEquals(Arrays.asList(incident1, incident2), actual);
    }

    @Test
    void testGetIncidentsByComponentId_NoIncidentsByComponentId() {
        List<Incident> actual = incidentService.getIncidentsByComponentId(911L);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testCreateIncident_Success() {
        IncidentDTO incidentDTO = createIncidentDTO();

        Incident actual = incidentService.createIncident(incidentDTO);

        assertEquals(incident3, actual);
    }

    @Test
    void testUpdateIncident_Success() {
        IncidentDTO incidentDTO = createIncidentDTO();
        incidentDTO.setStatus(IncidentStatus.CLOSED.name());

        Incident actual = incidentService.updateIncident(3L, incidentDTO);

        assertEquals(incident4, actual);
    }

    @Test
    void testUpdateIncident_WrongIncidentId() {
        assertThrows(NotFoundException.class, () -> incidentService.updateIncident(991L, createIncidentDTO()));
    }

    @Test
    void testUpdateIncident_WrongIncidentTypeId() {
        IncidentDTO incidentDTO = createIncidentDTO();
        incidentDTO.setIncidentTypeId(2L);

        assertThrows(NotFoundException.class, () -> incidentService.updateIncident(1L, incidentDTO));
    }

    @Test
    void testUpdateIncident_WrongComponentId() {
        IncidentDTO incidentDTO = createIncidentDTO();
        incidentDTO.setComponentId(2L);

        assertThrows(NotFoundException.class, () -> incidentService.updateIncident(1L, incidentDTO));
    }

    @Test
    void testUpdateIncident_WrongUserId() {
        IncidentDTO incidentDTO = createIncidentDTO();
        incidentDTO.setUserID(2L);

        assertThrows(NotFoundException.class, () -> incidentService.updateIncident(1L, incidentDTO));
    }

    private IncidentDTO createIncidentDTO() {
        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setDescription("Description goes here");
        incidentDTO.setIncidentTypeId(1L);
        incidentDTO.setComponentId(1L);
        incidentDTO.setUserID(1L);
        incidentDTO.setStatus(IncidentStatus.OPEN.name());
        incidentDTO.setStartDate(startDate);
        incidentDTO.setEndDate(endDate);

        return incidentDTO;
    }
}