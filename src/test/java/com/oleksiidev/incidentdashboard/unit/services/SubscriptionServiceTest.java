package com.oleksiidev.incidentdashboard.unit.services;

import com.google.common.collect.Sets;
import com.oleksiidev.incidentdashboard.dto.SubscriptionDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.model.Subscription;
import com.oleksiidev.incidentdashboard.repositories.SubscriptionRepository;
import com.oleksiidev.incidentdashboard.services.ComponentService;
import com.oleksiidev.incidentdashboard.services.IncidentTypeService;
import com.oleksiidev.incidentdashboard.services.PlatformService;
import com.oleksiidev.incidentdashboard.services.RegionService;
import com.oleksiidev.incidentdashboard.services.ServiceService;
import com.oleksiidev.incidentdashboard.services.SubscriptionService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (MockitoExtension.class)
class SubscriptionServiceTest {

    private final SubscriptionService subscriptionService;

    public SubscriptionServiceTest(@Mock SubscriptionRepository subscriptionRepository,
                                   @Mock IncidentTypeService incidentTypeService,
                                   @Mock PlatformService platformService,
                                   @Mock RegionService regionService,
                                   @Mock ServiceService serviceService,
                                   @Mock ComponentService componentService) {
        subscriptionService = new SubscriptionService(subscriptionRepository, incidentTypeService, platformService,
                regionService, serviceService, componentService);

        IncidentType incidentType = new IncidentType();
        incidentType.setId(1L);
        incidentType.setName("Incident Type 1");

        Platform platform = new Platform();
        platform.setId(1L);
        platform.setName("Platform 1");

        Region region = new Region();
        region.setId(1L);
        region.setName("Region 1");

        Service service = new Service();
        service.setId(1L);
        service.setName("Service 1");
        service.setPlatform(platform);

        Component component = new Component();
        component.setName(RandomStringUtils.randomAlphabetic(10));
        component.setService(service);
        component.setRegions(Sets.newHashSet(region));
        component.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setEmail("user@email.coo");
        subscription.setPlatform(platform);
        subscription.setService(service);
        subscription.setIncidentType(incidentType);
        subscription.setRegion(region);
        subscription.setId(1L);

        Mockito.when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        Mockito.when(incidentTypeService.findIncidentTypeById(1L)).thenReturn(Optional.of(incidentType));
        Mockito.when(platformService.findPlatformById(1L)).thenReturn(Optional.of(platform));
        Mockito.when(serviceService.findServiceById(1L)).thenReturn(Optional.of(service));
        Mockito.when(regionService.findRegionById(1L)).thenReturn(Optional.of(region));
        Mockito.when(componentService.findComponentById(1L)).thenReturn(Optional.of(component));
        Mockito.when(componentService.getComponentsByServiceId(1L)).thenReturn(Arrays.asList(component));
    }

    @Test
    void testCreateSubscription_Success() {
        SubscriptionDTO subscriptionDTO = createSubscriptionDTO();

        assertTrue(subscriptionService.createSubscription(subscriptionDTO));
    }

    @Test
    void testCreateSubscription_FailOnEmptyDTO() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.createSubscription(subscriptionDTO));
    }

    @Test
    void testCreateSubscription_BlankEmail() {
        SubscriptionDTO subscriptionDTO = createSubscriptionDTO();
        subscriptionDTO.setEmail("");

        assertThrows(IllegalArgumentException.class, () -> subscriptionService.createSubscription(subscriptionDTO));
    }

    @Test
    void testCreateSubscription_WrongPlatform() {
        SubscriptionDTO subscriptionDTO = createSubscriptionDTO();
        subscriptionDTO.setPlatformId(2L);

        assertThrows(NotFoundException.class, () -> subscriptionService.createSubscription(subscriptionDTO));
    }

    @Test
    void testCreateSubscription_WrongService() {
        SubscriptionDTO subscriptionDTO = createSubscriptionDTO();
        subscriptionDTO.setServiceIds(Collections.singletonList(2L));

        assertThrows(NotFoundException.class, () -> subscriptionService.createSubscription(subscriptionDTO));
    }

    @Test
    void testCreateSubscription_WrongIncidentType() {
        SubscriptionDTO subscriptionDTO = createSubscriptionDTO();
        subscriptionDTO.setIncidentTypeIds(Collections.singletonList(2L));

        assertThrows(NotFoundException.class, () -> subscriptionService.createSubscription(subscriptionDTO));
    }

    @Test
    void testCreateSubscription_WrongRegion() {
        SubscriptionDTO subscriptionDTO = createSubscriptionDTO();
        subscriptionDTO.setRegionIds(Collections.singletonList(2L));

        assertFalse(subscriptionService.createSubscription(subscriptionDTO));
    }

    private SubscriptionDTO createSubscriptionDTO() {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setEmail("user@email.coo");
        subscriptionDTO.setPlatformId(1L);
        subscriptionDTO.setServiceIds(Collections.singletonList(1L));
        subscriptionDTO.setIncidentTypeIds(Collections.singletonList(1L));
        subscriptionDTO.setRegionIds(Collections.singletonList(1L));
        return subscriptionDTO;
    }
}