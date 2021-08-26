package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping("/all")
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/platformId/{id}")
    public List<Service> getServicesForPlatformId(@PathVariable @NonNull Long platformId) {
        return serviceService.getServicesByPlatformId(platformId);
    }

    @GetMapping("/id/{id}")
    public Service getServiceById(@PathVariable @NonNull Long id) {
        return serviceService.findServiceById(id)
                .orElseThrow(() -> new NotFoundException(Service.class, id));
    }

    @PostMapping("/create")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public Service createService(@RequestParam @NonNull Long platformId,
                                 @RequestBody String serviceName) {
        return serviceService.createService(platformId, serviceName);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public Service updateServiceName(@PathVariable @NonNull Long id,
                                     @RequestBody String name) {
        return serviceService.updateServiceName(id, name);
    }

    @PatchMapping("/update/{serviceId}")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public Service updateServicePlatform(@PathVariable @NonNull Long serviceId,
                                         @RequestParam @NonNull Long platformId) {
        return serviceService.updateServicePlatform(serviceId, platformId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public void deleteService(@PathVariable @NonNull Long id) {
        serviceService.deleteService(id);
    }
}
