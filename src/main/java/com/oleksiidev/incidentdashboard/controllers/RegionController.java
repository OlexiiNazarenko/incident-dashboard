package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.services.RegionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/region")
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/id/{id}")
    public Region getRegionById(@PathVariable @NonNull Long id) {
        return regionService.findRegionById(id)
                .orElseThrow(() -> new NotFoundException(Region.class, id));
    }

    @GetMapping("/all")
    public Set<Region> getAllRegions() {
        return regionService.getAllRegions();
    }

    @PostMapping("/create")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public Region createRegion(@RequestBody @NonNull String name) {
        return regionService.createRegion(name);
    }

    @PutMapping("/update/id/{id}")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public Region updateregionName(@PathVariable @NonNull Long id,
                                   @RequestParam String newName) {
        return regionService.updateRegionName(id, newName);
    }

    @DeleteMapping("/delete/id/{id}")
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    public void deleteRegion(@PathVariable @NonNull Long id) {
        regionService.deleteRegion(id);
    }
}
