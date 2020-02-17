package com.lifesight.service.controller;

import com.lifesight.service.generated.Configuration;
import com.lifesight.service.manager.RateLimiterManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RatelimitController {

    @Autowired
    private RateLimiterManager manager;

    @PostMapping(value = "/updateRatelimits", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPixel(@RequestBody String configuration) {
        ResponseEntity<?> responseEntity = manager.refresh(configuration);
        return responseEntity;
    }
}
