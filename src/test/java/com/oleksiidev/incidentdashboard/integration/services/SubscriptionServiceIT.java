package com.oleksiidev.incidentdashboard.integration.services;

import com.google.common.collect.Sets;
import com.oleksiidev.incidentdashboard.dto.SubscriptionDTO;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.model.Subscription;
import com.oleksiidev.incidentdashboard.repositories.ComponentRepository;
import com.oleksiidev.incidentdashboard.repositories.IncidentTypeRepository;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import com.oleksiidev.incidentdashboard.repositories.SubscriptionRepository;
import com.oleksiidev.incidentdashboard.services.SubscriptionService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class SubscriptionServiceIT {

    private final SubscriptionService subscriptionService;

    private final SubscriptionRepository subscriptionRepository;
    private final IncidentTypeRepository incidentTypeRepository;
    private final PlatformRepository platformRepository;
    private final RegionRepository regionRepository;
    private final ServiceRepository serviceRepository;
    private final ComponentRepository componentRepository;

    @Autowired
    public SubscriptionServiceIT(SubscriptionService subscriptionService,
                                 SubscriptionRepository subscriptionRepository,
                                 IncidentTypeRepository incidentTypeRepository,
                                 PlatformRepository platformRepository,
                                 RegionRepository regionRepository,
                                 ServiceRepository serviceRepository,
                                 ComponentRepository componentRepository) {
        this.subscriptionService = subscriptionService;
        this.subscriptionRepository = subscriptionRepository;
        this.incidentTypeRepository = incidentTypeRepository;
        this.platformRepository = platformRepository;
        this.regionRepository = regionRepository;
        this.serviceRepository = serviceRepository;
        this.componentRepository = componentRepository;
    }

    @AfterEach
    void tearDown() {
        subscriptionRepository.deleteAll();
        incidentTypeRepository.deleteAll();
        platformRepository.deleteAll();
        regionRepository.deleteAll();
        serviceRepository.deleteAll();
        componentRepository.deleteAll();
    }

    @Test
    void testCreateSubscription() {
        IncidentType incidentType = new IncidentType();
        incidentType.setName("Incident Type 1");
        Long incidentTypeId = incidentTypeRepository.save(incidentType).getId();

        Platform platform = new Platform();
        platform.setName("Platform 1");
        Long platformId = platformRepository.save(platform).getId();

        Region region = new Region();
        region.setName("Region 1");
        Long regionId = regionRepository.save(region).getId();

        Service service = new Service();
        service.setName("Service 1");
        service.setPlatform(platform);
        Long serviceId = serviceRepository.save(service).getId();

        Component component = new Component();
        component.setName(RandomStringUtils.randomAlphabetic(10));
        component.setService(service);
        component.setRegions(Sets.newHashSet(region));
        componentRepository.save(component);

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setEmail("user@email.coo");
        subscriptionDTO.setPlatformId(platformId);
        subscriptionDTO.setServiceIds(Collections.singletonList(serviceId));
        subscriptionDTO.setIncidentTypeIds(Collections.singletonList(incidentTypeId));
        subscriptionDTO.setRegionIds(Collections.singletonList(regionId));

        assertTrue(subscriptionService.createSubscription(subscriptionDTO));

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        Long lastSubscriptionId = subscriptionList.get(subscriptionList.size() - 1).getId();

        Optional<Subscription> subscriptionSaved = subscriptionRepository.findById(lastSubscriptionId);
        assertTrue(subscriptionSaved.isPresent());
        assertEquals("user@email.coo", subscriptionSaved.get().getEmail());
        assertEquals(platform, subscriptionSaved.get().getPlatform());
        assertEquals(service, subscriptionSaved.get().getService());
        assertEquals(incidentType, subscriptionSaved.get().getIncidentType());
        assertEquals(region, subscriptionSaved.get().getRegion());
        assertEquals(lastSubscriptionId, subscriptionSaved.get().getId());
    }

    @Test
    void deleteAllSubscriptionsForEmail() {
        IncidentType incidentType = new IncidentType();
        incidentType.setName("Incident Type 1");
        IncidentType incidentTypeSaved = incidentTypeRepository.save(incidentType);

        Platform platform = new Platform();
        platform.setName("Platform 1");
        Platform platformSaved = platformRepository.save(platform);

        Region region = new Region();
        region.setName("Region 1");
        Region regionSaved = regionRepository.save(region);

        Service service = new Service();
        service.setName("Service 1");
        service.setPlatform(platform);
        Service serviceSaved = serviceRepository.save(service);

        Subscription subscription = new Subscription();
        subscription.setEmail("user@email.coo");
        subscription.setPlatform(platformSaved);
        subscription.setService(serviceSaved);
        subscription.setIncidentType(incidentTypeSaved);
        subscription.setRegion(regionSaved);

        subscriptionRepository.save(subscription);
        assertTrue(subscriptionRepository.findSubscriptionsByEmail("user@email.coo").size() > 0);

        subscriptionService.deleteAllSubscriptionsForEmail("user@email.coo");

        assertEquals(0, subscriptionRepository.findSubscriptionsByEmail("user@email.coo").size());
    }
}