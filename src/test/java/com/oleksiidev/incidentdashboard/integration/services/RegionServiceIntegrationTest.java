package com.oleksiidev.incidentdashboard.integration.services;

import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import com.oleksiidev.incidentdashboard.services.RegionService;
import org.apache.commons.lang3.RandomStringUtils;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class RegionServiceIntegrationTest {

    private final RegionService regionService;
    private final RegionRepository regionRepository;

    @Autowired
    public RegionServiceIntegrationTest(RegionService regionService, RegionRepository regionRepository) {
        this.regionService = regionService;
        this.regionRepository = regionRepository;
    }

    @AfterEach
    void tearDown() {
        regionRepository.deleteAll();
    }

    @Test
    void testGetRegionById() {
        Region expected = regionRepository.save(createRegion());

        Optional<Region> actual = regionService.findRegionById(expected.getId());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetRegionsByIds() {
        Region region1 = regionRepository.save(createRegion());
        Region region2 = regionRepository.save(createRegion());
        Region region3 = regionRepository.save(createRegion());

        Set<Region> actual = regionService.getRegionsByIds(Sets.newHashSet(region1.getId(), region2.getId(), region3.getId()));
        assertFalse(actual.isEmpty());
        assertEquals(Sets.newHashSet(region1, region2, region3), actual);

        actual = regionService.getRegionsByIds(Sets.newHashSet(region1.getId(), region2.getId()));
        assertFalse(actual.isEmpty());
        assertEquals(Sets.newHashSet(region1, region2), actual);

        actual = regionService.getRegionsByIds(Sets.newHashSet(region1.getId(), Long.MAX_VALUE));
        assertFalse(actual.isEmpty());
        assertEquals(Sets.newHashSet(region1), actual);

        actual = regionService.getRegionsByIds(Sets.newHashSet(Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE - 2));
        assertTrue(actual.isEmpty());
    }


    @Test
    void testGetAllRegions() {
        Region region1 = regionRepository.save(createRegion());
        Region region2 = regionRepository.save(createRegion());

        Set<Region> actual = regionService.getAllRegions();

        assertEquals(Sets.newHashSet(region1, region2), actual);
    }

    @Test
    void testCreateRegion() {
        String name = "Region Name";
        Region actual = regionService.createRegion(name);

        assertEquals(actual.getName(), name);
    }

    @Test
    void testUpdateRegionName() {
        Region region = regionRepository.save(createRegion());

        String newName = "New Name";
        Region updatedRegion = regionService.updateRegionName(region.getId(), newName);

        assertNotNull(updatedRegion);
        assertEquals(region.getId(), updatedRegion.getId());
        assertEquals(newName, updatedRegion.getName());
    }

    @Test
    void testDeleteRegion() {
        Long savedId = regionRepository.save(createRegion()).getId();

        regionService.deleteRegion(savedId);

        assertEquals(Optional.empty(), regionRepository.findById(savedId));
    }

    private Region createRegion() {
        Region region = new Region();
        region.setName(RandomStringUtils.randomAlphabetic(12));
        return region;
    }
}