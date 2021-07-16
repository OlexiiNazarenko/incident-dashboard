package com.oleksiidev.incidentdashboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class IncidentDTO {
    private String incidentTypeName;
    private Long userID;
    private long componentID;
    private String status;
    private String description;
    private Date startDate;
    private Date endDate;
}