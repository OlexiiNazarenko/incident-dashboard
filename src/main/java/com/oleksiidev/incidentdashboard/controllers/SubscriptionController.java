package com.oleksiidev.incidentdashboard.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @GetMapping("/subscribe")
    public HttpStatus subscribe(@RequestParam String email) {
        return HttpStatus.OK;
    }

    @GetMapping("/unsubscribe")
    public HttpStatus unsubscribe(@RequestParam String email) {
        return HttpStatus.OK;
    }
}
