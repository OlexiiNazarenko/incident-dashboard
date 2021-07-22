package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.ComponentDTO;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.services.ComponentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/component")
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/id/{id}")
    public Component getComponentById(@PathVariable @NonNull Long id) {
        return componentService.getComponentById(id);
    }

    @GetMapping("/all")
    public List<Component> getAllComponents() {
        return componentService.getAllComponents();
    }

    @GetMapping("/serviceId/{serviceId}")
    public List<Component> getComponentsByServiceId(@PathVariable @NonNull Long serviceId) {
        return componentService.getComponentsByServiceId(serviceId);
    }

    @PostMapping("/create")
    public Component createComponent(@RequestBody ComponentDTO componentDTO) {
        return componentService.createComponent(componentDTO);
    }

    @PutMapping("/update/{id}")
    public Component updateComponent(@PathVariable @NonNull Long id,
                                     @RequestBody ComponentDTO componentDTO) {
        return componentService.updateComponent(id, componentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComponent(@PathVariable @NonNull Long id) {
        componentService.deleteComponent(id);
    }
}
