package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final PlatformService platformService;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Service> findServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public List<Service> getServicesByPlatformId(Long platformId) {
        return serviceRepository.findServicesByPlatform(platformService.findPlatformById(platformId)
                .orElseThrow(() -> new NotFoundException("Platform", platformId)));
    }

    public Service createService(Long platformId, String serviceName) {
        Platform platform = platformService.findPlatformById(platformId)
                .orElseThrow(() -> new NotFoundException("Platform", platformId));

        Service newService = new Service();
        newService.setName(serviceName);
        newService.setPlatform(platform);

        return serviceRepository.save(newService);
    }

    public Service updateServiceName(Long id, String newName) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service", id));

        service.setName(newName);

        return serviceRepository.save(service);
    }

    public Service updateServicePlatform(Long serviceId, Long platformId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException("Service", serviceId));
        Platform platform = platformService.findPlatformById(platformId)
                .orElseThrow(() -> new NotFoundException("Platform", platformId));

        service.setPlatform(platform);

        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
