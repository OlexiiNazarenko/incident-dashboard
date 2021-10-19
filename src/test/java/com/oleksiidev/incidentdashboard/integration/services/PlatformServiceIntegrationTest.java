package com.oleksiidev.incidentdashboard.integration.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.services.PlatformService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class PlatformServiceIntegrationTest {

    private final PlatformService platformService;
    private final PlatformRepository platformRepository;

    @Autowired
    public PlatformServiceIntegrationTest(PlatformService platformService, PlatformRepository platformRepository) {
        this.platformService = platformService;
        this.platformRepository = platformRepository;
    }

    @AfterEach
    void tearDown() {
        platformRepository.deleteAll();
    }

    @Test
    void testGetPlatformById() {
        String name = "Platform Name";
        Platform platform = new Platform();
        platform.setName(name);

        Platform expected = platformRepository.save(platform);
        Platform actual = platformService.findPlatformById(expected.getId())
                .orElseThrow(() -> new NotFoundException("Saved Platform was not found in database by id"));

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllPlatforms() {
        Platform platform1 = new Platform();
        platform1.setName("Platform 1");

        Platform platform2 = new Platform();
        platform2.setName("Platform 2");

        List<Platform> expected = platformRepository.saveAll(Arrays.asList(platform1, platform2));
        List<Platform> actual = platformService.getAllPlatforms();

        assertEquals(expected, actual);
    }

    @Test
    void testCreatePlatform() {
        String name = "Platform Name";
        Platform actual = platformService.createPlatform(name);

        assertEquals(name, actual.getName());
    }

    @Test
    void testUpdatePlatform() {
        Platform platform = new Platform();
        String name = "Platform Name";
        platform.setName(name);
        Long savedId = platformRepository.save(platform).getId();

        String newName = "New Platform Name";
        Platform actual = platformService.updatePlatform(savedId, newName);

        assertEquals(newName, actual.getName());
    }

    @Test
    void deletePlatform() {
        Platform platform = new Platform();
        platform.setName("Platform Name");
        Long savedId = platformRepository.save(platform).getId();

        platformService.deletePlatform(savedId);

        assertEquals(Optional.empty(), platformRepository.findById(savedId));
    }
}