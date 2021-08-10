package com.oleksiidev.incidentdashboard.controllers;

import com.oleksiidev.incidentdashboard.dto.SubscriptionDTO;
import com.oleksiidev.incidentdashboard.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/subscribe")
    public boolean subscribe(@RequestBody SubscriptionDTO subscriptionDTO) {
        return subscriptionService.createSubscription(subscriptionDTO);
    }

    @GetMapping("/unsubscribe")
    public boolean unsubscribe(@RequestParam String email) {
        subscriptionService.deleteAllSubscriptionsForEmail(email);
        return true;
    }
}
