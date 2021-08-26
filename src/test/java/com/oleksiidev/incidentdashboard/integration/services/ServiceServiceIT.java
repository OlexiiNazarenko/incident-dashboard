package com.oleksiidev.incidentdashboard.integration.services;

import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import com.oleksiidev.incidentdashboard.services.ServiceService;
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

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class ServiceServiceIT {

    private final ServiceService serviceService;
    private final ServiceRepository serviceRepository;
    private final PlatformRepository platformRepository;

    @Autowired
    public ServiceServiceIT(ServiceService serviceService, ServiceRepository serviceRepository, PlatformRepository platformRepository) {
        this.serviceService = serviceService;
        this.serviceRepository = serviceRepository;
        this.platformRepository = platformRepository;
    }

    @AfterEach
    void tearDown() {
        serviceRepository.deleteAll();
    }

    @Test
    void testGetAllServices() {
        Service service1 = serviceRepository.save(createService());
        Service service2 = serviceRepository.save(createService());

        List<Service> actual = serviceService.getAllServices();

        assertFalse(actual.isEmpty());
        assertEquals(Arrays.asList(service1, service2), actual);
    }

    @Test
    void testGetServiceById() {
        Service expected = serviceRepository.save(createService());

        Optional<Service> actual = serviceService.findServiceById(expected.getId());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetServicesByPlatformId() {
        Platform platform = createPlatformAndSaveToDatabase();

        Service service1 = saveServiceToDatabase(createService(platform));
        Service service2 = saveServiceToDatabase(createService(platform));
        Service service3 = saveServiceToDatabase(createService(createPlatformAndSaveToDatabase()));

        List<Service> actual = serviceService.getServicesByPlatformId(platform.getId());

        assertTrue(serviceRepository.findById(service3.getId()).isPresent());
        assertEquals(Arrays.asList(service1, service2), actual);
    }

    @Test
    void testCreateService() {
        Platform platform = createPlatformAndSaveToDatabase();
        String serviceName = RandomStringUtils.randomAlphabetic(12);

        Service actual = serviceService.createService(platform.getId(), serviceName);

        assertNotNull(actual);
        assertEquals(platform, actual.getPlatform());
        assertEquals(serviceName, actual.getName());
    }

    @Test
    void testUpdateServiceName() {
        Service service =  serviceRepository.save(createService());

        String newServiceName = RandomStringUtils.randomAlphabetic(21);
        Service actual = serviceService.updateServiceName(service.getId(), newServiceName);

        assertEquals(service.getId(), actual.getId());
        assertEquals(newServiceName, actual.getName());
    }

    @Test
    void testChangeServicePlatform() {
        Platform newPlatform = createPlatformAndSaveToDatabase();
        Service service = serviceRepository.save(createService());

        Service actual = serviceService.updateServicePlatform(service.getId(), newPlatform.getId());

        assertEquals(service.getId(), actual.getId());
        assertEquals(service.getName(), actual.getName());
        assertEquals(newPlatform, actual.getPlatform());
    }

    @Test
    void testDeleteService() {
        Service service = serviceRepository.save(createService());
        assertTrue(serviceRepository.existsById(service.getId()));

        serviceService.deleteService(service.getId());

        assertFalse(serviceRepository.existsById(service.getId()));
    }

    private Service createService() {
        return createService(createPlatformAndSaveToDatabase());
    }

    private Service createService(Platform platform) {
        Service service = new Service();
        service.setName(RandomStringUtils.randomAlphabetic(15));
        service.setPlatform(platform);

        return service;
    }

    private Service saveServiceToDatabase(Service service) {
        return serviceRepository.save(service);
    }

    private Platform createPlatformAndSaveToDatabase() {
        Platform platform = new Platform();
        platform.setName(RandomStringUtils.randomAlphabetic(15));
        return platformRepository.save(platform);
    }
}