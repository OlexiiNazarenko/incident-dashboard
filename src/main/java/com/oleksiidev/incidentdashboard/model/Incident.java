package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "`INCIDENT`")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`TypeID`")
    private IncidentType type;

    @ManyToOne(fetch = FetchType.LAZY)
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
