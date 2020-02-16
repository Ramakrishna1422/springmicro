package com.lifesight.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String name;
    private Role role = Role.USER;

    public User(Integer id, String name, Role role) {
        this.name = name;
        this.role = role;
        this.id = id;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }
}
