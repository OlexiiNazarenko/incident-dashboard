package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.services.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
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
@RequestMapping("/api/platform")
public class PlatformController {

    private final PlatformService platformService;

    @GetMapping("/id/{id}")
    public Platform getPlatformById(@PathVariable @NonNull Long id) {
        return platformService.getPlatformById(id)
                .orElseThrow(() -> new NotFoundException(Platform.class, id));
    }

    @GetMapping("/all")
    public List<Platform> getAllPlatforms() {
        return platformService.getAllPlatforms();
    }

    @PostMapping("/create")
    public Platform createPlatform(@RequestBody String name) {
        return platformService.createPlatform(name);
    }

    @PutMapping("/update/id/{id}")
    public Platform updatePlatform(@PathVariable @NonNull Long id,
                                   @RequestBody String name) {
        return platformService.updatePlatform(id, name);
    }

    @DeleteMapping("/delete/id/{id}")
    public void deletePlatform(@PathVariable @NonNull Long id) {
        platformService.deletePlatform(id);
    }
}
