package com.moldavets.restful_web_services.service.Impl;

import com.moldavets.restful_web_services.entity.User;
import com.moldavets.restful_web_services.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class UserServiceImpl implements UserService {

    private static List<User> users = new ArrayList<>();
    private static Integer usersCount = 0;

    static {
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Katy", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "John", LocalDate.now().minusYears(20)));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(int id) {
        Predicate<User> predicate = user -> user.getId().equals(id);
        User user = users.stream().filter(predicate).findFirst().orElse(null);

        return user;
    }

    @Override
    public void deleteById(int id) {
        Predicate<User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }

    @Override
    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }
}
