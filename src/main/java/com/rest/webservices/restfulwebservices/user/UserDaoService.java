package com.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Ryan", new Date()));
        users.add(new User(2, "Adam", new Date()));
        users.add(new User(3, "Jonny", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) user.setId(users.size() + 1);
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        List<User> matches = users.stream().filter(user -> user.getId() == id).collect(Collectors.toList());
        if (matches.size() == 0) return null;
        return matches.get(0);
    }

    public User deleteById(int id) {
        List<User> matches = users.stream().filter(user -> user.getId() == id).collect(Collectors.toList());
        if (matches.size() == 0) return null;
        users.remove(matches.get(0));
        return matches.get(0);
    }

}
