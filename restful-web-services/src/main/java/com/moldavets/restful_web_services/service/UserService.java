package com.moldavets.restful_web_services.service;

import com.moldavets.restful_web_services.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findById(int id);
    void deleteById(int id);
}
