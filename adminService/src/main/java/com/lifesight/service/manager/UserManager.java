package com.lifesight.service.manager;

import com.lifesight.service.model.Role;
import com.lifesight.service.model.User;
import com.lifesight.service.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserManager {

    @Autowired
    AdminRepository repository;

    public ResponseEntity<?> getUsers(Role role) {
        try {
            List<User> users = repository.getUsersByRole(role);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getUserById(Integer id) {
        try {
            if(id != null) {
                User user = repository.getUserById(id);
                if(user != null) {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
                } else {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("User not exists");
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("User id shouldn't be null");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }

    public ResponseEntity<?> createUser(User user) {
        try {
            if(user != null && user.getName() != null) {
                if(!repository.isExists(user)) {
                    user = repository.createUser(user);
                    if(user != null) {
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
                    } else {
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Unable to create user");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("User exists");
                }
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("User details shouldn't be null");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }

    public ResponseEntity<?> dropUserById(Integer id) {
        try {
            if(id != null) {
                User user = repository.getUserById(id);
                if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not exists");
                repository.dropUser(user);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("User id shouldn't be null");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }
}
