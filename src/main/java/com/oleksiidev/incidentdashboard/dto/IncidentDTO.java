package com.oleksiidev.incidentdashboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class IncidentDTO {
    private Long incidentTypeId;
    private Long userID;
    private long componentId;
    private String status;
    private String description;
    private Date startDate;
    private Date endDate;
}