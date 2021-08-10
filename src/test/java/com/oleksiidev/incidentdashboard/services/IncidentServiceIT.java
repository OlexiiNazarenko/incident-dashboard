package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.IncidentDTO;
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
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import com.oleksiidev.incidentdashboard.repositories.UserRepository;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class IncidentServiceIT {

    private final IncidentRepository incidentRepository;
    private final IncidentService incidentService;
    private final IncidentTypeRepository incidentTypeRepository;
    private final ComponentRepository componentRepository;
    private final RegionRepository regionRepository;
    private final PlatformRepository platformRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IncidentServiceIT(IncidentRepository incidentRepository,
                             IncidentService incidentService,
                             IncidentTypeRepository incidentTypeRepository,
                             ComponentRepository componentRepository,
                             RegionRepository regionRepository,
                             PlatformRepository platformRepository,
                             ServiceRepository serviceRepository,
                             UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentService = incidentService;
        this.incidentTypeRepository = incidentTypeRepository;
        this.componentRepository = componentRepository;
        this.regionRepository = regionRepository;
        this.platformRepository = platformRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void tearDown() {
        incidentTypeRepository.deleteAll();
    }

    @Test
    void testGetIncidentById() {
        Incident expected = createIncidentAndSaveToDatabase();

        Optional<Incident> actual = incidentService.findIncidentById(expected.getId());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetIncidentsByStatus() {
        Incident incident1 = createIncidentAndSaveToDatabase(IncidentStatus.OPEN);
        Incident incident2 = createIncidentAndSaveToDatabase(IncidentStatus.OPEN);
        assertNotEquals(incident1, incident2);

        List<Incident> actual = incidentService.getIncidentsByStatus(IncidentStatus.OPEN.name());

        assertEquals(Arrays.asList(incident1, incident2), actual);
    }

    @Test
    void testGetIncidentsByComponentId() {
        Component component = createComponent();
        Incident incident = createIncidentAndSaveToDatabase(component);

        List<Incident> actual = incidentService.getIncidentsByComponentId(component.getId());

        assertEquals(Collections.singletonList(incident), actual);
    }

    @Test
    void testCreateIncident() {
        IncidentType incidentType = createIncidentType();
        User user = createUser();
        Component component = createComponent();
        IncidentStatus status = IncidentStatus.OPEN;
        String description = RandomStringUtils.randomAlphabetic(100);
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setIncidentTypeId(incidentType.getId());
        incidentDTO.setUserID(user.getId());
        incidentDTO.setComponentId(component.getId());
        incidentDTO.setStatus(status.name());
        incidentDTO.setDescription(description);
        incidentDTO.setStartDate(startDate);
        incidentDTO.setEndDate(endDate);

        Incident incident = incidentService.createIncident(incidentDTO);

        assertEquals(incidentType, incident.getType());
        assertEquals(user, incident.getCreator());
        assertEquals(component, incident.getComponent());
        assertEquals(description, incident.getDescription());
        assertEquals(status, incident.getStatus());
        assertEquals(startDate, incident.getStartDate());
        assertEquals(endDate, incident.getEndDate());
    }

    @Test
    void testUpdateIncident() {
        Incident incident1 = createIncidentAndSaveToDatabase();
        Incident incident2 = createIncident();

        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setIncidentTypeId(incident2.getType().getId());
        incidentDTO.setUserID(incident2.getCreator().getId());
        incidentDTO.setComponentId(incident2.getComponent().getId());
        incidentDTO.setStatus(incident2.getStatus().name());
        incidentDTO.setDescription(incident2.getDescription());
        incidentDTO.setStartDate(incident2.getStartDate());
        incidentDTO.setEndDate(incident2.getEndDate());

        Incident actual = incidentService.updateIncident(incident1.getId(), incidentDTO);

        assertEquals(incident1.getId(), actual.getId());
        assertEquals(incident2.getType(), actual.getType());
        assertEquals(incident2.getCreator(), actual.getCreator());
        assertEquals(incident2.getComponent(), actual.getComponent());
        assertEquals(incident2.getDescription(), actual.getDescription());
        assertEquals(incident2.getStatus(), actual.getStatus());
        assertEquals(incident2.getStartDate(), actual.getStartDate());
        assertEquals(incident2.getEndDate(), actual.getEndDate());
    }

    @Test
    void testDeleteIncident() {
        Incident incident = createIncidentAndSaveToDatabase();
        assertNotNull(incidentRepository.findById(incident.getId()));

        incidentService.deleteIncident(incident.getId());
        assertEquals(Optional.empty(), incidentRepository.findById(incident.getId()));
    }

    private Incident createIncident(IncidentType type,
                                    Component component,
                                    User creator,
                                    IncidentStatus status,
                                    String description,
                                    Date startDate,
                                    Date endDate) {
        Incident incident = new Incident();

        incident.setType(type);
        incident.setComponent(component);
        incident.setCreator(creator);
        incident.setStatus(status);
        incident.setDescription(description);
        incident.setStartDate(startDate);
        incident.setEndDate(endDate);

        return incident;
    }

    private Incident createIncident() {
        IncidentType incidentType = createIncidentType();
        Component component = createComponent();
        User creator = createUser();
        IncidentStatus status = IncidentStatus.OPEN;
        String description = RandomStringUtils.randomAlphabetic(100);
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

        return createIncident(incidentType, component, creator, status, description, startDate, endDate);
    }

    private Incident createIncidentAndSaveToDatabase() {
        return incidentRepository.save(createIncident());
    }

    private Incident createIncidentAndSaveToDatabase(IncidentStatus status) {
        Incident incident = createIncident();
        incident.setStatus(status);
        return incidentRepository.save(incident);
    }

    private Incident createIncidentAndSaveToDatabase(Component component) {
        Incident incident = createIncident();
        incident.setComponent(component);
        return incidentRepository.save(incident);
    }


    private IncidentType createIncidentType(String name) {
        IncidentType incidentType = new IncidentType();
        incidentType.setName(name);
        return incidentTypeRepository.save(incidentType);
    }

    private IncidentType createIncidentType() {
        String name = RandomStringUtils.randomAlphabetic(10);
        return createIncidentType(name);
    }

    private Component createComponent() {
        Region region = createRegion();
        Service service = createService();

        Component component = new Component();
        component.setName("Component " + RandomStringUtils.randomAlphabetic(6));
        component.setRegions(Sets.newHashSet(region));
        component.setService(service);

        return componentRepository.save(component);
    }

    private Component createComponent(Service service) {
        Component component = createComponent();
        component.setService(service);
        return componentRepository.save(component);
    }

    private Region createRegion() {
        Region region = new Region();
        region.setName("Region " + RandomStringUtils.randomAlphabetic(6));
        return regionRepository.save(region);
    }

    private Platform createPlatform() {
        Platform platform = new Platform();
        platform.setName("Platform " + RandomStringUtils.randomAlphabetic(6));
        return platformRepository.save(platform);
    }

    private Service createService() {
        Platform platform = createPlatform();

        Service service = new Service();
        service.setName("Service " + RandomStringUtils.randomAlphabetic(6));
        service.setPlatform(platform);
        return  serviceRepository.save(service);
    }

    private Service createService(Platform platform) {
        Service service = new Service();
        service.setName("Service " + RandomStringUtils.randomAlphabetic(6));
        service.setPlatform(platform);
        return  serviceRepository.save(service);
    }

    private User createUser() {
        User user = new User();
        user.setUsername(RandomStringUtils.randomAlphabetic(12));
        user.setEmail(RandomStringUtils.randomAlphabetic(12) + "@email.ok");
        user.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(12)));
        user.setRole(Role.ROLE_ADMIN);
        return userRepository.save(user);
    }
}