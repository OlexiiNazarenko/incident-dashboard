package com.oleksiidev.incidentdashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "`SERVICE`")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`")
    private long id;

    @Column(name = "`Name`")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`PlatformID`")
    @JsonBackReference
    private Platform platform;

    @OneToMany(mappedBy = "service")
    @JsonManagedReference
    private List<Component> components;
}
