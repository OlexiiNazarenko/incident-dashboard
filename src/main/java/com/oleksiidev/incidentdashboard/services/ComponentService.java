package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.dto.ComponentDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.ComponentRepository;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final ServiceRepository serviceRepository;
    private final RegionRepository regionRepository;

    public Optional<Component> getComponentById(Long id) {
        return componentRepository.findById(id);
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public List<Component> getComponentsByServiceId(Long serviceId) {
        return componentRepository.findComponentsByService(serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(Service.class, serviceId)));
    }

    public Component createComponent(ComponentDTO componentDTO) {
        Service service = serviceRepository.findById(componentDTO.getServiceId())
                .orElseThrow(() -> new NotFoundException(Service.class, componentDTO.getServiceId()));

        Set<Region> regions = regionRepository.findRegionsByIdIn(componentDTO.getRegionsIds());
        if (ObjectUtils.isEmpty(regions)) {
            throw new NotFoundException("No region was found for ids: " + componentDTO.getRegionsIds());
        }

        Component newComponent = new Component();
        newComponent.setName(componentDTO.getName());
        newComponent.setService(service);
        newComponent.setRegions(regions);

        return componentRepository.save(newComponent);
    }

    public Component updateComponent(Long id, ComponentDTO componentDTO) {
        if (StringUtils.isBlank(componentDTO.getName())) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Component.class, id));

        Service service = serviceRepository.findById(componentDTO.getServiceId())
                .orElseThrow(() -> new NotFoundException(Service.class, componentDTO.getServiceId()));

        Set<Region> regions = regionRepository.findRegionsByIdIn(componentDTO.getRegionsIds());
        if (ObjectUtils.isEmpty(regions)) {
            throw new NotFoundException("No region was found for ids: " + componentDTO.getRegionsIds());
        }

        component.setName(componentDTO.getName());
        component.setService(service);
        component.setRegions(regions);

        return componentRepository.save(component);
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}
