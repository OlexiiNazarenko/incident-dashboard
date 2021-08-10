package com.oleksiidev.incidentdashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "`COMPONENT`")
public class Component extends BaseModel {

    @Column(name = "`Name`")
    private String name;

    @ManyToOne
    @JoinColumn(name = "`ServiceID`")
    @JsonBackReference
    private Service service;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "`COMPONENT_REGIONS`",
            joinColumns = @JoinColumn(name = "`ComponentID`"),
            inverseJoinColumns = @JoinColumn(name = "`RegionID`")
    )
    @JsonBackReference
    private Set<Region> regions;
}
