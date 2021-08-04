package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class ServiceServiceIT {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @AfterEach
    void tearDown() {
        serviceRepository.deleteAll();
    }

    @Test
    void testGetAllServices() {
        List<Service> expected = new ArrayList<>(2);

        Platform platform1 = new Platform();
        platform1.setName("Platform 1");
        Platform savedPlatform1 = platformRepository.save(platform1);
        Service service1 = new Service();
        service1.setName("Service 1");
        service1.setPlatform(savedPlatform1);
        expected.add(serviceRepository.save(service1));

        Platform platform2 = new Platform();
        platform2.setName("Platform 2");
        Platform savedPlatform2 = platformRepository.save(platform2);
        Service service2 = new Service();
        service2.setName("Service 2");
        service2.setPlatform(savedPlatform2);
        expected.add(serviceRepository.save(service2));

        List<Service> actual = serviceService.getAllServices();

        assertEquals(actual, expected);
    }

    @Test
    void testGetServiceById() {
        Platform platform1 = new Platform();
        platform1.setName("Platform 1");
        Platform savedPlatform1 = platformRepository.save(platform1);
        Service service1 = new Service();
        service1.setName("Service 1");
        service1.setPlatform(savedPlatform1);

        Service expected = serviceRepository.save(service1);

        Service actual = serviceService.getServiceById(expected.getId())
                .orElseThrow(() -> new NotFoundException("Saved service was not found in database by id"));

        assertEquals(actual, expected);
    }

    @Test
    void testGetServicesByPlatformId() {
        List<Service> expected = new ArrayList<>(2);

        Platform platform = new Platform();
        platform.setName("Platform ");
        Platform savedPlatform = platformRepository.save(platform);

        Service service1 = new Service();
        service1.setName("Service 1");
        service1.setPlatform(savedPlatform);
        expected.add(serviceRepository.save(service1));

        Service service2 = new Service();
        service2.setName("Service 2");
        service2.setPlatform(savedPlatform);
        expected.add(serviceRepository.save(service2));

        List<Service> actual = serviceService.getServicesByPlatformId(platform.getId());

        assertEquals(actual, expected);
    }

    @Test
    void testCreateService() {
        Platform platform = new Platform();
        platform.setName("Platform ");
        Long savedPlatformID = platformRepository.save(platform).getId();

        Service newService = new Service();
        String serviceName = "Service 1";
        newService.setName(serviceName);
        newService.setPlatform(platform);

        Service expected = serviceRepository.save(newService);

        Service actual = serviceService.createService(savedPlatformID, serviceName);

        assertEquals(actual, expected);
    }

    @Test
    void testUpdateServiceName() {
        Platform platform = new Platform();
        platform.setName("Platform ");
        Platform savedPlatform = platformRepository.save(platform);

        Service service = new Service();
        service.setName("Service 1");
        service.setPlatform(savedPlatform);
        Long savedServiceID = serviceRepository.save(service).getId();

        String newServiceName = "Service 2";
        Service actual = serviceService.updateServiceName(savedServiceID, newServiceName);

        assertEquals(actual.getName(), newServiceName);
    }

    @Test
    void testChangeServicePlatform() {
        Platform platform1 = new Platform();
        platform1.setName("Platform ");
        Platform savedPlatform1 = platformRepository.save(platform1);

        Platform platform2 = new Platform();
        platform1.setName("Platform ");
        Long savedPlatform2ID = platformRepository.save(platform2).getId();

        Service service = new Service();
        service.setName("Service 1");
        service.setPlatform(savedPlatform1);
        Long savedServiceID = serviceRepository.save(service).getId();

        Service actual = serviceService.updateServicePlatform(savedServiceID, savedPlatform2ID);

        assertEquals(actual.getPlatform(), platform2);
    }

    @Test
    void testDeleteService() {
        Platform platform = new Platform();
        platform.setName("Platform ");
        Platform savedPlatform = platformRepository.save(platform);

        Service service = new Service();
        service.setName("Service 1");
        service.setPlatform(savedPlatform);
        Long savedServiceID = serviceRepository.save(service).getId();

        serviceService.deleteService(savedServiceID);

        assertFalse(serviceRepository.existsById(savedServiceID));
    }
}