package com.oleksiidev.incidentdashboard;

import com.oleksiidev.incidentdashboard.configurations.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties (AppProperties.class)
public class IncidentDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentDashboardApplication.class, args);
    }

}
