package com.oleksiidev.incidentdashboard.integration.services;

import com.oleksiidev.incidentdashboard.dto.ComponentDTO;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.ComponentRepository;
import com.oleksiidev.incidentdashboard.repositories.PlatformRepository;
import com.oleksiidev.incidentdashboard.repositories.RegionRepository;
import com.oleksiidev.incidentdashboard.repositories.ServiceRepository;
import com.oleksiidev.incidentdashboard.services.ComponentService;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (value = SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class ComponentServiceIntegrationTest {

    private final ComponentService componentService;
    private final ComponentRepository componentRepository;
    private final RegionRepository regionRepository;
    private final PlatformRepository platformRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public ComponentServiceIntegrationTest(ComponentService componentService,
                                           ComponentRepository componentRepository,
                                           RegionRepository regionRepository,
                                           PlatformRepository platformRepository,
                                           ServiceRepository serviceRepository) {
        this.componentService = componentService;
        this.componentRepository = componentRepository;
        this.regionRepository = regionRepository;
        this.platformRepository = platformRepository;
        this.serviceRepository = serviceRepository;
    }

    @AfterEach
    void tearDown() {
        componentRepository.deleteAll();
    }

    @Test
    void testGetComponentById() {
        Component expected = createComponentAndSaveToDatabase();
        Optional<Component> actual = componentService.findComponentById(expected.getId());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetAllComponents() {
        List<Component> expected = new ArrayList<>(2);
        Component component1 = createComponentAndSaveToDatabase();
        Component component2 = createComponentAndSaveToDatabase();
        expected.addAll(Arrays.asList(component1, component2));

        List<Component> actual = componentService.getAllComponents();

        assertEquals(expected, actual);
    }

    @Test
    void testGetComponentsByServiceId() {
        Service commonService = createServiceAndSaveToDatabase();
        Component component1 = createComponentAndSaveToDatabase(commonService);
        Component component2 = createComponentAndSaveToDatabase(commonService);
        Component component3 = createComponentAndSaveToDatabase();

        List<Component> actual = componentService.getComponentsByServiceId(commonService.getId());

        assertTrue(actual.contains(component1));
        assertTrue(actual.contains(component2));
        assertFalse(actual.contains(component3));
    }

    @Test
    void testCreateComponent() {
        Region region1 = createRegionAndSaveToDatabase();
        Region region2 = createRegionAndSaveToDatabase();
        Service service = createServiceAndSaveToDatabase();
        String componentName = "Component " + RandomStringUtils.randomAlphabetic(6);

        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName(componentName);
        componentDTO.setServiceId(service.getId());
        componentDTO.setRegionsIds(Sets.newHashSet(region1.getId(), region2.getId()));

        Component component = componentService.createComponent(componentDTO);

        assertNotNull(component);
        assertEquals(componentName, component.getName());
        assertEquals(service, component.getService());
        assertEquals(Sets.newHashSet(region1, region2), component.getRegions());
    }

    @Test
    void testUpdateComponent() {
        Component component = createComponentAndSaveToDatabase();
        Component newComponent = createComponent();

        Component actual = componentService.updateComponent(component.getId(), componentToDTO(newComponent));

        assertAll(
                () -> assertEquals(newComponent.getName(), actual.getName()),
                () -> assertEquals(newComponent.getService(), actual.getService()),
                () -> assertEquals(newComponent.getRegions(), actual.getRegions())
        );


    }

    @Test
    void testDeleteComponent() {
        Component component = createComponentAndSaveToDatabase();

        componentService.deleteComponent(component.getId());

        assertEquals(Optional.empty(), componentRepository.findById(component.getId()));
    }

    private Component createComponent() {
        Region region = createRegionAndSaveToDatabase();
        Service service = createServiceAndSaveToDatabase();
        String componentName = "Component " + RandomStringUtils.randomAlphabetic(6);

        return createComponent(region, service, componentName);
    }

    private Component createComponent(Service service) {
        Region region = createRegionAndSaveToDatabase();
        String componentName = "Component " + RandomStringUtils.randomAlphabetic(6);

        return createComponent(region, service, componentName);
    }

    private Component createComponent(Region region, Service service, String name) {
        Component component = new Component();
        component.setName(name);
        component.setRegions(Sets.newHashSet(region));
        component.setService(service);

        return component;
    }

    private Component createComponentAndSaveToDatabase() {
        return componentRepository.save(createComponent());
    }

    private Component createComponentAndSaveToDatabase(Service service) {
        return componentRepository.save(createComponent(service));
    }

    private Region createRegionAndSaveToDatabase() {
        Region region = new Region();
        region.setName("Region " + RandomStringUtils.randomAlphabetic(6));
        return regionRepository.save(region);
    }

    private Service createServiceAndSaveToDatabase() {
        Platform platform = new Platform();
        platform.setName("Platform " + RandomStringUtils.randomAlphabetic(6));
        Platform savedPlatform = platformRepository.save(platform);

        Service service = new Service();
        service.setName("Service " + RandomStringUtils.randomAlphabetic(6));
        service.setPlatform(savedPlatform);
        return  serviceRepository.save(service);
    }

    private ComponentDTO componentToDTO(Component component) {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName(component.getName());
        componentDTO.setServiceId(component.getService().getId());
        componentDTO.setRegionsIds(regionIdsFromRegions(component.getRegions()));
        return componentDTO;
    }

    private Set<Long> regionIdsFromRegions(Set<Region> regions) {
        Set<Long> ids = new HashSet<>();
        for (Region region : regions) {
            ids.add(region.getId());
        }
        return ids;
    }
}