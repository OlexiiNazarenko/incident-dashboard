package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.ComponentDTO;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.repositories.ComponentRepository;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final ServiceRepository serviceRepository;
    private final RegionRepository regionRepository;

    public Component getComponentById(Long id) {
        return componentRepository.findComponentById(id);
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public List<Component> getComponentsByServiceId(Long serviceId) {
        return componentRepository.findComponentsByService(serviceRepository.findServiceById(serviceId));
    }

    public Component createComponent(ComponentDTO componentDTO) {
        Component newComponent = new Component();
        newComponent.setName(componentDTO.getName());
        newComponent.setService(serviceRepository.findServiceById(componentDTO.getServiceId()));
        newComponent.setRegions(regionRepository.findRegionsByIdIn(componentDTO.getRegionsIds()));
        return componentRepository.save(newComponent);
    }

    public Component updateComponent(Long id, ComponentDTO componentDTO) {
        Component component = componentRepository.findComponentById(id);
        component.setName(componentDTO.getName());
        component.setService(serviceRepository.findServiceById(componentDTO.getServiceId()));
        component.setRegions(regionRepository.findRegionsByIdIn(componentDTO.getRegionsIds()));
        return componentRepository.save(component);
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}
