package com.lifesight.service.manager;

import com.lifesight.service.repository.RateLimiterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RateLimiterManager {

    @Autowired
    RateLimiterRepository repository;

    public ResponseEntity<?> refresh(String json) {
        if(repository.init(json)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Configuration updated");
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Failed to update Configuration.");
        }
    }
}
