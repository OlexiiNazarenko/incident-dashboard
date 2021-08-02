package com.oleksiidev.incidentdashboard.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "`PLATFORM`")
public class Platform extends BaseModel {

    @Column(name = "`Name`")
    private String name;
}
