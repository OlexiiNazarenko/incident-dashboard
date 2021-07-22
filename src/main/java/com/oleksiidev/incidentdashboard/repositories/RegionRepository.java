package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RegionRepository extends CrudRepository<Region, Long> {

    Region findRegionById(Long id);

    Set<Region> findRegionsByIdIn(Set<Long> ids);
}
