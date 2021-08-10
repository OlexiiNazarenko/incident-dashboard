package com.oleksiidev.incidentdashboard.unit.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;

import com.oleksiidev.incidentdashboard.services.PlatformService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (MockitoExtension.class)
class PlatformServiceTest {
    private static final String UPDATED_PLATFORM_NAME = "Updated Platform Name";
    private static final String NEW_PLATFORM_NAME = "Platform 3";

    private final PlatformService platformService;
    private final Platform platform1;
    private final Platform updatedPlatform1;
    private final Platform platform2;
    private final Platform savedPlatform;

    PlatformServiceTest(@Mock PlatformRepository platformRepository) {
        platform1 = new Platform();
        platform1.setId(1L);
        platform1.setName("Platform 1");

        updatedPlatform1 = new Platform();
        updatedPlatform1.setId(1L);
        updatedPlatform1.setName(UPDATED_PLATFORM_NAME);

        platform2 = new Platform();
        platform2.setId(2L);
        platform2.setName("Platform 2");

        Platform platformToSave = new Platform();
        platformToSave.setName(NEW_PLATFORM_NAME);

        savedPlatform = new Platform();
        savedPlatform.setId(3L);
        savedPlatform.setName(NEW_PLATFORM_NAME);

        platformService = new PlatformService(platformRepository);

        Mockito.when(platformRepository.findById(1L)).thenReturn(Optional.of(platform1));
        Mockito.when(platformRepository.findById(2L)).thenReturn(Optional.of(platform2));
        Mockito.when(platformRepository.findAll()).thenReturn(Arrays.asList(platform1, platform2));
        Mockito.when(platformRepository.save(platformToSave)).thenReturn(savedPlatform);
        Mockito.when(platformRepository.save(updatedPlatform1)).thenReturn(updatedPlatform1);
    }

    @Test
    void testGetPlatformById_Success() {
        Optional<Platform> actual = platformService.findPlatformById(1L);
        assertTrue(actual.isPresent());
        assertEquals(platform1, actual.get());
    }

    @Test
    void testGetPlatformById_ReturnNullForInappropriateId() {
        Optional<Platform> actual = platformService.findPlatformById(4L);
        assertFalse(actual.isPresent());
    }

    @Test
    void getAllPlatforms() {
        List<Platform> actual = platformService.getAllPlatforms();
        assertEquals(Arrays.asList(platform1, platform2), actual);
    }

    @Test
    void testCreatePlatform() {
        Platform actual = platformService.createPlatform(NEW_PLATFORM_NAME);
        assertEquals(savedPlatform, actual);
    }

    @Test
    void testUpdatePlatform_Success() {
        Platform actual = platformService.updatePlatform(1L, UPDATED_PLATFORM_NAME);
        assertEquals(updatedPlatform1, actual);
    }

    @Test
    void testUpdatePlatform_ThrowNotFoundExceptionForInappropriateId() {
        assertThrows(NotFoundException.class, () -> platformService.updatePlatform(4L, UPDATED_PLATFORM_NAME));
    }
}