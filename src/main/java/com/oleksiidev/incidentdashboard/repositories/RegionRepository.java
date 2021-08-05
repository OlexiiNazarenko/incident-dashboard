package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Set<Region> findRegionsByIdIn(Set<Long> ids);
}
