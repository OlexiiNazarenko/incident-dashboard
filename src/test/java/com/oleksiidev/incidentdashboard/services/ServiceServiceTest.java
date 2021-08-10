package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (MockitoExtension.class)
class ServiceServiceTest {
    private static final String UPDATED_SERVICE_NAME = "Updated Service Name";
    private static final String NEW_SERVICE_NAME = "New Service";
    private static final Long PLATFORM_ID_TO_UPDATE_SERVICE = 2L;

    private final ServiceService serviceService;
    private final Service service1p1;
    private final Service service3p2;
    private final Service service2p1;
    private final Service updatedService1;
    private final Service updatedService2;
    private final Service savedService;

    public ServiceServiceTest(@Mock ServiceRepository serviceRepository,
                              @Mock PlatformService platformService) {
        serviceService = new ServiceService(serviceRepository, platformService);

        Platform platform1 = new Platform();
        platform1.setId(1L);
        platform1.setName("Platform 1");

        Platform platform2 = new Platform();
        platform2.setId(2L);
        platform2.setName("Platform 2");

        service1p1 = new Service();
        service1p1.setId(1L);
        service1p1.setName("Service 1");
        service1p1.setPlatform(platform1);

        service2p1 = new Service();
        service2p1.setId(2L);
        service2p1.setName("Service 2");
        service2p1.setPlatform(platform1);

        service3p2 = new Service();
        service3p2.setId(3L);
        service3p2.setName("Service 3");
        service3p2.setPlatform(platform2);

        updatedService1 = new Service();
        updatedService1.setId(1L);
        updatedService1.setName(UPDATED_SERVICE_NAME);
        updatedService1.setPlatform(platform1);

        updatedService2 = new Service();
        updatedService2.setId(2L);
        updatedService2.setName("Service 2");
        updatedService2.setPlatform(platform2);

        savedService = new Service();
        savedService.setId(4L);
        savedService.setName(NEW_SERVICE_NAME);
        savedService.setPlatform(platform2);

        Service serviceToSave = new Service();
        serviceToSave.setPlatform(platform2);
        serviceToSave.setName(NEW_SERVICE_NAME);

        Mockito.when(serviceRepository.findById(1L)).thenReturn(Optional.of(service1p1));
        Mockito.when(serviceRepository.findById(2L)).thenReturn(Optional.of(service2p1));
        Mockito.when(platformService.findPlatformById(1L)).thenReturn(Optional.of(platform1));
        Mockito.when(serviceRepository.findServicesByPlatform(platform1)).thenReturn(Arrays.asList(service1p1, service2p1));
        Mockito.when(serviceRepository.findServicesByPlatform(platform2)).thenReturn(Collections.singletonList(service3p2));
        Mockito.when(platformService.findPlatformById(2L)).thenReturn(Optional.of(platform2));
        Mockito.when(serviceRepository.findAll()).thenReturn(Arrays.asList(service1p1, service2p1, service3p2));
        Mockito.when(serviceRepository.save(serviceToSave)).thenReturn(savedService);
        Mockito.when(serviceRepository.save(updatedService1)).thenReturn(updatedService1);
        Mockito.when(serviceRepository.save(updatedService2)).thenReturn(updatedService2);
    }

    @Test
    void testGetAllServices() {
        List<Service> actual = serviceService.getAllServices();
        assertEquals(actual, Arrays.asList(service1p1, service2p1, service3p2));
    }

    @Test
    void testGetServiceById_Success() {
        Optional<Service> actual = serviceService.findServiceById(1L);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), service1p1);
    }

    @Test
    void testGetServiceById_ReturnNullForInappropriateId() {
        Optional<Service> actual = serviceService.findServiceById(5L);
        assertFalse(actual.isPresent());
    }

    @Test
    void testGetServicesByPlatformId_Success() {
        List<Service> servicesForPlatform1 = serviceService.getServicesByPlatformId(1L);
        assertEquals(servicesForPlatform1, Arrays.asList(service1p1, service2p1));
        List<Service> servicesForPlatform2 = serviceService.getServicesByPlatformId(2L);
        assertEquals(servicesForPlatform2, Collections.singletonList(service3p2));
    }

    @Test
    void testGetServicesByPlatformId_ThrowPlatformNotFoundExceptionForInappropriatePlatformId() {
        assertThrows(NotFoundException.class, () -> serviceService.getServicesByPlatformId(3L));
    }

    @Test
    void testCreateService_Success() {
        Service actual = serviceService.createService(2L, NEW_SERVICE_NAME);
        assertEquals(actual, savedService);
    }

    @Test
    void testCreateService_ThrowPlatformNotFoundExceptionForInappropriatePlatformId() {
        assertThrows(NotFoundException.class, () -> serviceService.createService(3L, NEW_SERVICE_NAME));
    }

    @Test
    void testUpdateServiceName_Success() {
        Service actual = serviceService.updateServiceName(1L, UPDATED_SERVICE_NAME);
        assertEquals(actual, updatedService1);
    }

    @Test
    void testUpdateServiceName_ThrowNotFoundExceptionForInappropriateId() {
        assertThrows(NotFoundException.class, () -> serviceService.updateServiceName(9L, UPDATED_SERVICE_NAME));
    }

    @Test
    void testUpdateServicePlatform() {
        Service actual = serviceService.updateServicePlatform(2L, PLATFORM_ID_TO_UPDATE_SERVICE);
        assertEquals(actual, updatedService2);
    }

    @Test
    void testUpdateServicePlatform_ThrowPlatformNotFoundExceptionForInappropriatePlatformId() {
        assertThrows(NotFoundException.class, () -> serviceService.updateServicePlatform(2l, 9L));
    }
}