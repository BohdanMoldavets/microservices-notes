package com.moldavets.restful_web_services.controller;


import com.moldavets.restful_web_services.dao.UserRepository;
import com.moldavets.restful_web_services.entity.Post;
import com.moldavets.restful_web_services.entity.User;
import com.moldavets.restful_web_services.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jpa/users")
public class UserJpaRestController {

    private final UserRepository userRepository;

    @Autowired
    public UserJpaRestController(UserRepository userService) {
        this.userRepository = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable Integer id) {
        User tempUser = userRepository.findById(id).orElse(null);
        if (tempUser == null) {
            throw new UserNotFoundException("id:" + id);
        }

        EntityModel<User> entityModel = EntityModel.of(tempUser);

        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserJpaRestController.class).getAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User storedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(storedUser);
    }

    @GetMapping("/{id}/posts")
    public List<Post> deleteUserById(@PathVariable Integer id) {
        User tempUser = userRepository.findById(id).orElse(null);
        if (tempUser == null) {
            throw new UserNotFoundException("id:" + id);
        }

        return tempUser.getPosts();
    }
}
