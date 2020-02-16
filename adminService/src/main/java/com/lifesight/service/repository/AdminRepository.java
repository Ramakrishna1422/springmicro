package com.lifesight.service.repository;

import com.lifesight.service.model.Role;
import com.lifesight.service.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class AdminRepository {

    private List<User> users = new ArrayList<>();

    public AdminRepository() {
        users.add(new User(101, "Aaron", Role.ADMIN));
        users.add(new User(102, "Daniel", Role.USER));
        users.add(new User(103, "Rama", Role.ADMIN));
        users.add(new User(104, "Ezra", Role.USER));
        users.add(new User(105, "Thomas", Role.ADMIN));
        users.add(new User(106, "Caleb", Role.USER));
        users.add(new User(107, "Jack", Role.ADMIN));
        users.add(new User(108, "Luke", Role.USER));
    }


    public List<User> getUsersByRole(Role role) {
        if(role != null) {
            return users.stream()
                    .filter(user -> user.getRole() == role)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(users);
    }

    public User getUserById(Integer id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    public User createUser(User user) {
        Integer userId = users.stream().max(Comparator.comparingInt(User::getId)).get().getId();
        user.setId(++userId);
        if(users.add(user)) {
            return user;
        }
        return null;
    }

    public boolean isExists(User user) {
        log.info(user.toString());
        return users.stream().
                filter(user1 -> (user1.getName().equalsIgnoreCase(user.getName()) && user1.getRole() == user.getRole()))
                .findFirst().isPresent();
    }

    public boolean dropUser(User user) {
        return users.remove(user);
    }
}
