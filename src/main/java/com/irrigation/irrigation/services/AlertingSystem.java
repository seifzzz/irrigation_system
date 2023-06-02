package com.irrigation.irrigation.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


//Class Using to Alert Client can't make request.
@Service
public class AlertingSystem  {
    public ResponseEntity<String> alertSensorNotAvailable() {
        // Implement the alerting mechanism (e.g., send an email, trigger a notification, etc.)
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Sensor not available. Added plot to the queue. " +
                "It will irrigate after sensor is available ");
        }
    }


