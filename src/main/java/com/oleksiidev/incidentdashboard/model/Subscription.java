package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "`SUBSCRIPTION`")
public class Subscription extends BaseModel {

    @Column(name = "`Email`")
    private String email;

    @ManyToOne
    @JoinColumn(name = "`PlatformID`")
    private Platform platform;

    @ManyToOne
    @JoinColumn(name = "`ServiceID`")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "`IncidentTypeID`")
    private IncidentType incidentType;

    @ManyToOne
    @JoinColumn(name = "`RegionID`")
    private Region region;
}
