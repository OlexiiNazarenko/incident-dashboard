package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.auth.JwtProvider;
import com.oleksiidev.incidentdashboard.dto.AuthenticationDTO;
import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping ("/register")
    public User registerUser(@RequestBody @Validated RegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO);
    }

    @PostMapping("/api/login")
    public String auth(@RequestBody AuthenticationDTO authenticationDTO) {
        userService.authenticate(authenticationDTO.getUsername(), authenticationDTO.getPassword());
        return jwtProvider.generateToken(authenticationDTO.getUsername());
    }
}
