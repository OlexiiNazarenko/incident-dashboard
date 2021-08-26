package com.oleksiidev.incidentdashboard.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class IncidentDTO {
    private Long incidentTypeId;
    private Long userID;
    private long componentId;
    private String status;
    private String description;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}