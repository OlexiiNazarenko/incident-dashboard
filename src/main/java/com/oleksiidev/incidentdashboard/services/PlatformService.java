package com.oleksiidev.incidentdashboard.services;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlatformService {

    private final PlatformRepository platformRepository;

    public Optional<Platform> findPlatformById(Long id) {
        return platformRepository.findById(id);
    }

    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }

    public Platform createPlatform(String platformName) {
        Platform newPlatform = new Platform();
        newPlatform.setName(platformName);
        return platformRepository.save(newPlatform);
    }

    public Platform updatePlatform(Long id, String newPlatformName) {
        Platform platform = platformRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Platform.class, id));
        platform.setName(newPlatformName);
        return platformRepository.save(platform);
    }

    public void deletePlatform(Long id) {
        platformRepository.deleteById(id);
    }
}
