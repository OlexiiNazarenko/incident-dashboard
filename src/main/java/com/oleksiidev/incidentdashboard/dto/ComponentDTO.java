package com.oleksiidev.incidentdashboard.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ComponentDTO {
    private String name;
    private Long serviceId;
    private Set<Long> regionsIds;
}
