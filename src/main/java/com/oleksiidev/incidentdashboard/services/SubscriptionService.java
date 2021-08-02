package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.SubscriptionDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
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
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class SubscriptionService {

    private final IncidentTypeRepository incidentTypeRepository;
    private final PlatformRepository platformRepository;
    private final RegionRepository regionRepository;
    private final ServiceRepository serviceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ComponentRepository componentRepository;

    public boolean createSubscription(SubscriptionDTO subscriptionDTO) {
        Platform platform = platformRepository.findById(subscriptionDTO.getPlatformId())
                .orElseThrow(() -> new NotFoundException(Platform.class, subscriptionDTO.getPlatformId()));

        for (Long serviceId : subscriptionDTO.getServiceIds()) {
            Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new NotFoundException(Service.class, serviceId));

            if(service.getPlatform().getId() != subscriptionDTO.getPlatformId()) continue;

            for (Long regionId : subscriptionDTO.getRegionIds()) {
                if (!getAllRegionIdsForService(service).contains(regionId)) continue;
                Region region = regionRepository.findById(regionId)
                        .orElseThrow(() -> new NotFoundException(Region.class, regionId));

                for (Long incidentTypeId : subscriptionDTO.getIncidentTypeIds()) {
                    IncidentType incidentType = incidentTypeRepository.findById(incidentTypeId)
                            .orElseThrow(() -> new NotFoundException("Incident Type", incidentTypeId));

                    Subscription newSubscription = new Subscription();
                    newSubscription.setEmail(subscriptionDTO.getEmail());
                    newSubscription.setPlatform(platform);
                    newSubscription.setService(service);
                    newSubscription.setIncidentType(incidentType);
                    newSubscription.setRegion(region);

                    subscriptionRepository.save(newSubscription);
                }
            }
        }

        return true;
    }

    public boolean deleteAllSubscriptionsForEmail(String email) {
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionsByEmail(email);
        subscriptionRepository.deleteAll(subscriptions);
        return true;
    }

    private Set<Long> getAllRegionIdsForService(Service service) {
        Set<Long> ids = new HashSet<>();
        List<Component> components = componentRepository.findComponentsByService(service);
        components.forEach(c -> ids.add(c.getId()));
        return ids;
    }
}
