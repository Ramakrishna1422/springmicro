package com.lifesight.service.controller;


import com.lifesight.service.manager.PixelManager;
import com.lifesight.service.model.Pixel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "/v1")
public class PixelController {

    @Autowired
    PixelManager pixelManager;

    @PostMapping(value = "/createPixel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPixel(@RequestBody Pixel pixel) {
        ResponseEntity<?> responseEntity = pixelManager.createPixel(pixel);
        return responseEntity;
    }

    @GetMapping(value = "/getPixel/{name}")
    public ResponseEntity<?> getPixel(@Valid @PathVariable("name") String name) {
        ResponseEntity<?> responseEntity = pixelManager.getPixelByName(name);
        return responseEntity;
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updatePixel(@RequestBody Pixel pixel) {
        ResponseEntity<?> responseEntity = pixelManager.updatePixel(pixel);
        return responseEntity;
    }

    @GetMapping(value = "/getAllPixels")
    public ResponseEntity<?> getAllPixels() {
        ResponseEntity<?> responseEntity = pixelManager.getAllPixels();
        return responseEntity;
    }

    @DeleteMapping(value = "/dropPixel/{name}")
    public ResponseEntity<?> dropPixel(@Valid @PathVariable("name") String name) {
        ResponseEntity<?> responseEntity = pixelManager.dropPixel(name);
        return responseEntity;
    }
}
