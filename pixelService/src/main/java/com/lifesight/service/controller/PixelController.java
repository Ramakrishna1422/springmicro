package com.lifesight.service.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PixelController {

    @RequestMapping(value = "/admin")
    public String available() {
        return "Spring in Action";
    }

    @RequestMapping(value = "/list")
    public String checkedOut() {
        return "Spring Boot in Action";
    }
}
