package com.oleksiidev.incidentdashboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionDTO {

    private String email;
    private Long platformId;
    private List<Long> serviceIds;
    private List<Long> componentIds;
    private List<Long> incidentTypeIds;
    private List<Long> regionIds;
}
