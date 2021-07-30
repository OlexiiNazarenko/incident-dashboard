package com.oleksiidev.incidentdashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "`USER`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`")
    private long id;

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
