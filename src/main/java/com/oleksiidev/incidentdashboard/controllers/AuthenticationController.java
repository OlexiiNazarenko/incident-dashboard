package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.auth.TokenProvider;
import com.oleksiidev.incidentdashboard.dto.ApiResponseDTO;
import com.oleksiidev.incidentdashboard.dto.AuthResponseDTO;
import com.oleksiidev.incidentdashboard.dto.AuthenticationDTO;
import com.oleksiidev.incidentdashboard.dto.RegistrationDTO;
import com.oleksiidev.incidentdashboard.exceptions.BadRequestException;
import com.oleksiidev.incidentdashboard.model.User;
import com.oleksiidev.incidentdashboard.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userService.authenticate(authenticationDTO.getEmail(), authenticationDTO.getPassword());

        String token = tokenProvider.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody RegistrationDTO registrationDTO) {
        if(userService.findUserByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email address already in use.");
        }

        User result = userService.registerUser(registrationDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/current")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseDTO(true, "User registered successfully"));
    }
}
