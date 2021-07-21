package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(name = "COMPONENT")
public class Component {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ServiceID")
    private Service service;

    @ManyToMany
    @JoinTable(
            name = "COMPONENT_REGIONS",
            joinColumns = @JoinColumn(name = "RegionID"),
            inverseJoinColumns = @JoinColumn(name = "ComponentID")
    )
    private Set<Region> regions;
}
