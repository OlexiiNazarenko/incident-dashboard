package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
@Entity
public class Component {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @ManyToOne
    private Service service;

    @ManyToMany
    private Set<Region> regions;
}
