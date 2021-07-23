package com.oleksiidev.incidentdashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ServiceID")
    @JsonBackReference
    private Service service;

    @ManyToMany
    @JoinTable(
            name = "COMPONENT_REGIONS",
            joinColumns = @JoinColumn(name = "ComponentID"),
            inverseJoinColumns = @JoinColumn(name = "RegionID")
    )
    private Set<Region> regions;
}
