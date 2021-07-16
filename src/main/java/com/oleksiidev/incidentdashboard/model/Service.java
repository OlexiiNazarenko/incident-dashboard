package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Service {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private Platform platform;
}
