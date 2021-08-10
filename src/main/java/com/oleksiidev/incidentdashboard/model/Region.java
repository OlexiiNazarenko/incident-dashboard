package com.oleksiidev.incidentdashboard.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode (callSuper = true)
@ToString (callSuper = true)
@Data
@Entity
@Table(name = "`REGION`")
public class Region extends BaseModel {

    @Column(name = "`Name`")
    private String name;
}
