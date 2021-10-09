package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.SubscriptionDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.IncidentType;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.model.Subscription;
import com.oleksiidev.incidentdashboard.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final IncidentTypeService incidentTypeService;
    private final PlatformService platformService;
    private final RegionService regionService;
    private final ServiceService serviceService;
    private final ComponentService componentService;

    public boolean createSubscription(SubscriptionDTO subscriptionDTO) {
        boolean result = false;

        if (StringUtils.isBlank(subscriptionDTO.getEmail())) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

        Platform platform = platformService.findPlatformById(subscriptionDTO.getPlatformId())
                .orElseThrow(() -> new NotFoundException("Platform", subscriptionDTO.getPlatformId()));

        for (Long serviceId : subscriptionDTO.getServiceIds()) {
            Service service = serviceService.findServiceById(serviceId)
                    .orElseThrow(() -> new NotFoundException("Service", serviceId));

            if(!service.getPlatform().getId().equals(subscriptionDTO.getPlatformId())) continue;

            for (Long regionId : subscriptionDTO.getRegionIds()) {
                if (!getAllRegionIdsForService(service).contains(regionId)) continue;
                Region region = regionService.findRegionById(regionId)
                        .orElseThrow(() -> new NotFoundException("Region", regionId));

                for (Long incidentTypeId : subscriptionDTO.getIncidentTypeIds()) {
                    IncidentType incidentType = incidentTypeService.findIncidentTypeById(incidentTypeId)
                            .orElseThrow(() -> new NotFoundException("Incident Type", incidentTypeId));

                    Subscription newSubscription = new Subscription();
                    newSubscription.setEmail(subscriptionDTO.getEmail());
                    newSubscription.setPlatform(platform);
                    newSubscription.setService(service);
                    newSubscription.setIncidentType(incidentType);
                    newSubscription.setRegion(region);

                    subscriptionRepository.save(newSubscription);
                    result = true;
                }
            }
        }

        return result;
    }

    public void deleteAllSubscriptionsForEmail(String email) {
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionsByEmail(email);
        subscriptionRepository.deleteAll(subscriptions);
    }

    private Set<Long> getAllRegionIdsForService(Service service) {
        Set<Long> ids = new HashSet<>();

        List<Component> components = componentService.getComponentsByServiceId(service.getId());

        if (ObjectUtils.isEmpty(components)) throw new NotFoundException("No Components were found for Service: " + service.getName());

        components.forEach(c -> c.getRegions().forEach(r -> ids.add(r.getId())));

        return ids;
    }
}
