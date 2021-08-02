package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "`INCIDENT_TYPE`")
public class IncidentType extends BaseModel {

    @Column(name = "`Name`")
    private String name;
}
