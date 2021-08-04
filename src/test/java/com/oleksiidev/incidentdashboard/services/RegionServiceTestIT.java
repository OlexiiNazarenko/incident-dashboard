package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class RegionServiceTestIT {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionRepository regionRepository;

    @AfterEach
    void tearDown() {
        regionRepository.deleteAll();
    }

    @Test
    void testGetRegionById() {
        String name = "Test Region 1";
        Region region = new Region();
        region.setName(name);

        Region expected = regionRepository.save(region);
        Region actual = regionService.getRegionById(region.getId())
                .orElseThrow(() -> new NotFoundException("Saved region was not found in database by id"));

        assertEquals(actual, expected);
    }

    @Test
    void testGetAllRegions() {
        Region region1 = new Region();
        region1.setName("Test Region 1");
        Region region2 = new Region();
        region2.setName("Test Region 2");

        Set<Region> expected = new HashSet<>(regionRepository.saveAll(Arrays.asList(region1, region2)));
        Set<Region> actual = regionService.getAllRegions();

        assertEquals(actual, expected);
    }

    @Test
    void testCreateRegion() {
        String name = "Region Name";
        Region actual = regionService.createRegion(name);

        assertEquals(actual.getName(), name);
    }

    @Test
    void testUpdateRegionName() {
        String name = "Test Region 1";
        Region region = new Region();
        region.setName(name);
        Long savedId = regionRepository.save(region).getId();

        String newName = "Test Region 2";
        Region actual = regionService.updateRegionName(savedId, newName);

        assertEquals(actual.getName(), newName);
    }

    @Test
    void testDeleteRegion() {
        String name = "Test Region 1";
        Region region = new Region();
        region.setName(name);
        Long savedId = regionRepository.save(region).getId();

        regionService.deleteRegion(savedId);

        assertNull(regionRepository.findById(savedId));
    }
}