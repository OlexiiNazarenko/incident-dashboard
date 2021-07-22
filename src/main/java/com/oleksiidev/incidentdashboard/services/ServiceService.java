package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final PlatformService platformService;

    public List<Service> getAllServices() {
        return (List<Service>) serviceRepository.findAll();
    }

    public Service getServiceById(Long id) {
        return serviceRepository.findServiceById(id);
    }

    public List<Service> getServicesByPlatformId(Long platformId) {
        return serviceRepository.findServicesByPlatform(platformService.getPlatformById(platformId));
    }

    public Service createService(Long platformId, String serviceName) {
        Service newService = new Service();
        newService.setName(serviceName);
        newService.setPlatform(platformService.getPlatformById(platformId));
        return serviceRepository.save(newService);
    }

    public Service updateServiceName(Long id, String newName) {
        Service service = serviceRepository.findServiceById(id);
        service.setName(newName);
        return serviceRepository.save(service);
    }

    public Service changeServicePlatform(Long serviceId, Long platformId) {
        Service service = serviceRepository.findServiceById(serviceId);
        service.setPlatform(platformService.getPlatformById(platformId));
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.delete(serviceRepository.findServiceById(id));
    }
}
