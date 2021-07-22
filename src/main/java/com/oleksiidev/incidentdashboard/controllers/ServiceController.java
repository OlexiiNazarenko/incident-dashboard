package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service")
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
        return serviceService.getServiceById(id);
    }

    @PostMapping("/create")
    public Service createService(@RequestParam @NonNull Long platformId,
                                 @RequestParam String serviceName) {
        return serviceService.createService(platformId, serviceName);
    }

    @PatchMapping("/update/{id}/name")
    public Service updateServiceName(@PathVariable @NonNull Long id,
                                     @RequestParam String name) {
        return serviceService.updateServiceName(id, name);
    }

    @PatchMapping("/update/{serviceId}/platformId/{platformId}")
    public Service updateServicePlatform(@PathVariable @NonNull Long serviceId,
                                         @PathVariable @NonNull Long platformId) {
        return serviceService.changeServicePlatform(serviceId, platformId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteService(@PathVariable @NonNull Long id) {
        serviceService.deleteService(id);
    }
}
