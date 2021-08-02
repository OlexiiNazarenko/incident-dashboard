package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public Region getRegionById(Long id) {
        return regionRepository.findRegionById(id);
    }

    public Set<Region> getAllRegions() {
        // TODO: Why hash set?
        return new HashSet<>(regionRepository.findAll());
    }

    public Region createRegion(String name) {
        Region newRegion = new Region();
        newRegion.setName(name);
        return regionRepository.save(newRegion);
    }

    public Region updateRegionName(Long id, String newName) {
        Region region = regionRepository.findRegionById(id);
        region.setName(newName);
        return regionRepository.save(region);
    }

    public void deleteRegion(Long id) {
        regionRepository.deleteById(id);
    }
}
