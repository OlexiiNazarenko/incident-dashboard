package com.oleksiidev.incidentdashboard.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "`REGION`")
public class Region extends BaseModel {

    @Column(name = "`Name`")
    private String name;
}
