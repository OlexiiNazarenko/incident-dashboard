package com.oleksiidev.incidentdashboard.dto;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String roleName;
    private String email;
}
