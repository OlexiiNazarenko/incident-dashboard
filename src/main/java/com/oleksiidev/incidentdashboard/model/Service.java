package com.oleksiidev.incidentdashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "`SERVICE`")
public class Service extends BaseModel {

    @Column(name = "`Name`")
    private String name;

    @ManyToOne
    @JoinColumn(name = "`PlatformID`")
    @JsonBackReference
    private Platform platform;
}
