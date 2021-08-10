package com.oleksiidev.incidentdashboard.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode (callSuper = true)
@Data
@Entity
@Table(name = "`INCIDENT`")
public class Incident extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "`TypeID`")
    private IncidentType type;

    @ManyToOne
    @JoinColumn(name = "`ComponentID`")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "`UserID`")
    private User creator;

    @Column(name = "`Status`", columnDefinition="ENUM('OPEN','CLOSED')")
    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    @Column(name = "`Description`", columnDefinition = "TEXT")
    private String description;

    @Column(name = "`StartDate`")
    private Date startDate;

    @Column(name = "`EndDate`")
    private Date endDate;
}
