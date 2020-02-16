package com.lifesight.service.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @RequestMapping(value = "/admin")
    public String available() {
        return "Spring in Action";
    }

    @RequestMapping(value = "/list")
    public String checkedOut() {
        return "Spring Boot in Action";
    }
}
