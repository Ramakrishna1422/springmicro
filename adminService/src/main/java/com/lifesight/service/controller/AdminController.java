package com.lifesight.service.controller;


import com.lifesight.service.manager.UserManager;
import com.lifesight.service.model.Role;
import com.lifesight.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdminController {

    @Autowired
    UserManager userManager;

    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        ResponseEntity<?> responseEntity = userManager.createUser(user);
        return responseEntity;
    }

    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<?> getUser(@Valid @PathVariable("id") Integer id) {
        ResponseEntity<?> responseEntity = userManager.getUserById(id);
        return responseEntity;
    }

    @GetMapping(value = "/getAdmins")
    public ResponseEntity<?> getAdminUser() {
        ResponseEntity<?> responseEntity = userManager.getUsers(Role.ADMIN);
        return responseEntity;
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<?> getAllUser() {
        ResponseEntity<?> responseEntity = userManager.getUsers(null);
        return responseEntity;
    }

    @DeleteMapping(value = "/dropUser/{id}")
    public ResponseEntity<?> dropUser(@Valid @PathVariable("id") Integer id) {
        ResponseEntity<?> responseEntity = userManager.dropUserById(id);
        return responseEntity;
    }

}
