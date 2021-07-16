package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Incident {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private IncidentType type;

    @ManyToOne
    private Component component;

    @ManyToOne
    private User creator;

    private IncidentStatus status;

    private String description;

    private Date startDate;

    private Date endDate;
}
