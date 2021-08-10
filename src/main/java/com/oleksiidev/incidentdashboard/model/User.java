package com.oleksiidev.incidentdashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@EqualsAndHashCode (callSuper = true)
@Data
@Entity
@Table(name = "`USER`")
public class User extends BaseModel {

    @Column(name = "`Username`")
    private String username;

    @Column(name = "`Role`", columnDefinition="ENUM('ROLE_ADMIN', 'ROLE_MANAGER')")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "`Password`")
    @JsonIgnore
    private String password;

    @Column(name = "`Email`")
    private String email;
}
