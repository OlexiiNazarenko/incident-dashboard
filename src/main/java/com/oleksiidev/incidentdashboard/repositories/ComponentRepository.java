package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Component;
import com.oleksiidev.incidentdashboard.model.Region;
import com.oleksiidev.incidentdashboard.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends CrudRepository<Component, Long> {

    Component findComponentById(Long id);

    List<Component> findComponentsByService(Service service);

    List<Component> findComponentsByRegions(List<Region> regions);
}
