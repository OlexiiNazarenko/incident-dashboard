package com.oleksiidev.incidentdashboard.unit.services;

import com.google.common.collect.Sets;
import com.oleksiidev.incidentdashboard.dto.ComponentDTO;
import com.oleksiidev.incidentdashboard.exceptions.NotFoundException;
import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Platform;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import com.oleksiidev.incidentdashboard.repositories.ComponentRepository;
import com.oleksiidev.incidentdashboard.services.ComponentService;
import com.oleksiidev.incidentdashboard.services.RegionService;
import com.oleksiidev.incidentdashboard.services.ServiceService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.gt;

@ExtendWith (MockitoExtension.class)
class ComponentServiceTest {

    private final ComponentService componentService;

    private final Component component1;
    private final Component component2;
    private final Component component3;

    public ComponentServiceTest(@Mock ComponentRepository componentRepository,
                                @Mock ServiceService serviceService,
                                @Mock RegionService regionService) {
        componentService = new ComponentService(componentRepository, serviceService, regionService);

        Platform platform1 = new Platform();
        platform1.setId(1L);
        platform1.setName("Platform 1");

        Platform platform2 = new Platform();
        platform2.setId(2L);
        platform2.setName("Platform 2");

        Service service1 = new Service();
        service1.setId(1L);
        service1.setName("Service 1");
        service1.setPlatform(platform1);

        Service service2 = new Service();
        service2.setId(2L);
        service2.setName("Service 2");
        service2.setPlatform(platform1);

        Region region1 = new Region();
        region1.setId(1L);
        region1.setName("Region 1");

        Region region2 = new Region();
        region2.setId(2L);
        region2.setName("Region 2");

        component1 = new Component();
        component1.setName(RandomStringUtils.randomAlphabetic(10));
        component1.setService(service1);
        component1.setRegions(Sets.newHashSet(region1, region2));
        component1.setId(1L);

        component2 = new Component();
        component2.setName(RandomStringUtils.randomAlphabetic(10));
        component2.setService(service1);
        component2.setRegions(Sets.newHashSet(region1));
        component2.setId(2L);

        Component componentToSave = new Component();
        componentToSave.setName("Component 3");
        componentToSave.setService(service1);
        componentToSave.setRegions(Sets.newHashSet(region1, region2));

        component3 = new Component();
        component3.setName("Component 3");
        component3.setService(service1);
        component3.setRegions(Sets.newHashSet(region1, region2));
        component3.setId(3L);

        Component component4 = new Component();
        component4.setName("Updated Name");
        component4.setService(service2);
        component4.setRegions(Sets.newHashSet(region1));
        component4.setId(1L);

        Mockito.when(componentRepository.findById(1L)).thenReturn(Optional.of(component1));
        Mockito.when(componentRepository.findById(2L)).thenReturn(Optional.of(component2));
        Mockito.when(componentRepository.findById(gt(2L))).thenReturn(Optional.empty());
        Mockito.when(componentRepository.findAll()).thenReturn(Arrays.asList(component1, component2));
        Mockito.when(componentRepository.findComponentsByService(service1)).thenReturn(Arrays.asList(component1, component2));
        Mockito.when(componentRepository.save(componentToSave)).thenReturn(component3);
        Mockito.when(componentRepository.save(component4)).thenReturn(component4);

        Mockito.when(serviceService.findServiceById(1L)).thenReturn(Optional.of(service1));
        Mockito.when(serviceService.findServiceById(2L)).thenReturn(Optional.of(service2));

        Mockito.when(regionService.findRegionById(1L)).thenReturn(Optional.of(region1));
        Mockito.when(regionService.findRegionById(2L)).thenReturn(Optional.of(region2));
        Mockito.when(regionService.getRegionsByIds(Sets.newHashSet(1L))).thenReturn(Sets.newHashSet(region1));
        Mockito.when(regionService.getRegionsByIds(Sets.newHashSet(2L))).thenReturn(Sets.newHashSet(region2));
        Mockito.when(regionService.getRegionsByIds(Sets.newHashSet(1L, 2L))).thenReturn(Sets.newHashSet(region1, region2));
    }

    @Test
    void testFindComponentById_Success() {
        Optional<Component> actual = componentService.findComponentById(1L);

        assertTrue(actual.isPresent());
        assertEquals(component1, actual.get());
    }

    @Test
    void testFindComponentById_WrongId() {
        Optional<Component> actual = componentService.findComponentById(99L);

        assertFalse(actual.isPresent());
    }

    @Test
    void testGetAllComponents_Success() {
        List<Component> actual = componentService.getAllComponents();

        assertFalse(actual.isEmpty());
        assertEquals(Arrays.asList(component1, component2), actual);
    }

    @Test
    void testGetComponentsByServiceId_Success() {
        List<Component> actual = componentService.getComponentsByServiceId(1L);

        assertFalse(actual.isEmpty());
        assertEquals(Arrays.asList(component1, component2), actual);
    }

    @Test
    void testGetComponentsByServiceId_WrongServiceId() {
        List<Component> actual = componentService.getComponentsByServiceId(2L);

        assertTrue(actual.isEmpty());
    }

    @Test
    void testCreateComponent_Success() {
        ComponentDTO componentDTO = createComponentDTO();
        componentDTO.setName("Component 3");

        Component actual = componentService.createComponent(componentDTO);

        assertEquals(component3, actual);
    }

    @Test
    void testCreateComponent_WrongServiceId() {
        ComponentDTO componentDTO = createComponentDTO();
        componentDTO.setServiceId(999L);

        assertThrows(NotFoundException.class, () -> componentService.createComponent(componentDTO));
    }

    @Test
    void testCreateComponent_WrongRegionId() {
        ComponentDTO componentDTO = createComponentDTO();
        componentDTO.setRegionsIds(Sets.newHashSet(1L, 999L));

        assertThrows(NotFoundException.class, () -> componentService.createComponent(componentDTO));
    }

    @Test
    void updateComponent_Success() {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Updated Name");
        componentDTO.setServiceId(2L);
        componentDTO.setRegionsIds(Sets.newHashSet(1L));

        Component actual = componentService.updateComponent(1L, componentDTO);

        assertEquals(1L, actual.getId());
        assertEquals(componentDTO.getName(), actual.getName());
        assertEquals(componentDTO.getServiceId(), actual.getService().getId());
        assertEquals(componentDTO.getRegionsIds().size(), actual.getRegions().size());
        for (Region region: actual.getRegions()) {
            assertTrue(componentDTO.getRegionsIds().contains(region.getId()));
        }
    }

    @Test
    void updateComponent_WrongComponentId() {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName(RandomStringUtils.randomAlphabetic(12));
        componentDTO.setServiceId(2L);
        componentDTO.setRegionsIds(Sets.newHashSet(1L));

        assertThrows(NotFoundException.class, () -> componentService.updateComponent(911L, componentDTO));
    }

    @Test
    void updateComponent_WrongServiceId() {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName(RandomStringUtils.randomAlphabetic(12));
        componentDTO.setServiceId(911L);
        componentDTO.setRegionsIds(Sets.newHashSet(1L));

        assertThrows(NotFoundException.class, () -> componentService.updateComponent(1L, componentDTO));
    }


    @Test
    void updateComponent_WrongRegionId() {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName(RandomStringUtils.randomAlphabetic(12));
        componentDTO.setServiceId(2L);
        componentDTO.setRegionsIds(Sets.newHashSet(911L));

        assertThrows(NotFoundException.class, () -> componentService.updateComponent(1L, componentDTO));
    }

    @Test
    void updateComponent_BlankComponentName() {
        ComponentDTO componentDTO = createComponentDTO();
        componentDTO.setName("");

        assertThrows(IllegalArgumentException.class, () -> componentService.updateComponent(1L, componentDTO));
    }

    private ComponentDTO createComponentDTO() {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName(RandomStringUtils.randomAlphabetic(12));
        componentDTO.setServiceId(1L);
        componentDTO.setRegionsIds(Sets.newHashSet(1L, 2L));
        return componentDTO;
    }
}