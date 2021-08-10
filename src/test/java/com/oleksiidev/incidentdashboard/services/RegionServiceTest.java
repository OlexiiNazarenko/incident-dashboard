package com.oleksiidev.incidentdashboard.services;

import com.google.common.collect.Sets;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith (MockitoExtension.class)
class RegionServiceTest {
    private static final String UPDATED_REGION_NAME = "Updated Region Name";
    private static final String NEW_REGION_NAME = "Region 3";

    private final RegionService regionService;
    private final Region region1;
    private final Region updatedRegion1;
    private final Region region2;
    private final Region savedRegion;

    RegionServiceTest(@Mock RegionRepository regionRepository) {
        regionService = new RegionService(regionRepository);

        region1 = new Region();
        region1.setId(1L);
        region1.setName("Region 1");

        updatedRegion1 = new Region();
        updatedRegion1.setId(1L);
        updatedRegion1.setName(UPDATED_REGION_NAME);

        region2 = new Region();
        region2.setId(2L);
        region2.setName("Region 2");

        Region regionToSave = new Region();
        regionToSave.setName(NEW_REGION_NAME);

        savedRegion = new Region();
        savedRegion.setId(3L);
        savedRegion.setName(NEW_REGION_NAME);

        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(region1));
        Mockito.when(regionRepository.findById(2L)).thenReturn(Optional.of(region2));
        Mockito.when(regionRepository.findAll()).thenReturn(Arrays.asList(region1, region2));
        Mockito.when(regionRepository.save(regionToSave)).thenReturn(savedRegion);
        Mockito.when(regionRepository.save(updatedRegion1)).thenReturn(updatedRegion1);
    }

    @Test
    void testGetRegionById_Success() {
        Optional<Region> actual = regionService.findRegionById(1L);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), region1);
    }

    @Test
    void testGetRegionById_ReturnNullForInappropriateId() {
        Optional<Region> actual = regionService.findRegionById(4L);
        assertFalse(actual.isPresent());
    }

    @Test
    void testGetAllRegions() {
        Set<Region> actual = regionService.getAllRegions();
        assertEquals(actual, Sets.newHashSet(region1, region2));
    }

    @Test
    void testCreateRegion() {
        Region actual = regionService.createRegion(NEW_REGION_NAME);
        assertEquals(actual, savedRegion);
    }

    @Test
    void testUpdateRegionName() {
        Region actual = regionService.updateRegionName(1L, UPDATED_REGION_NAME);
        assertEquals(actual, updatedRegion1);
    }
}