package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
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

    @Column (name = "UserId")
    @ManyToOne
    private User creator;

    @Column
    private IncidentStatus status;

    @Column
    private String description;

    @Column
    private Date startDate;

    @Column
    private Date endDate;
}
