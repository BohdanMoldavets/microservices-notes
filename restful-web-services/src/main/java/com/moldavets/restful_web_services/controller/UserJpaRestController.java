package com.moldavets.restful_web_services.controller;


import com.moldavets.restful_web_services.dao.PostRepository;
import com.moldavets.restful_web_services.dao.UserRepository;
import com.moldavets.restful_web_services.entity.Post;
import com.moldavets.restful_web_services.entity.User;
import com.moldavets.restful_web_services.exception.PostNotFoundException;
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
import java.util.Optional;

@RestController
@RequestMapping("/jpa/users")
public class UserJpaRestController {

    private UserRepository USER_REPOSITORY;
    private PostRepository POST_REPOSITORY;

    @Autowired
    public UserJpaRestController(UserRepository userService,
                                 PostRepository postRepository) {
        this.USER_REPOSITORY = userService;
        this.POST_REPOSITORY = postRepository;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return USER_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable Integer id) {
        User tempUser = USER_REPOSITORY.findById(id).orElse(null);
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
        User storedUser = USER_REPOSITORY.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(storedUser);
    }

    @GetMapping("/{id}/posts")
    public List<Post> getPostsByUserId(@PathVariable Integer id) {
        User tempUser = USER_REPOSITORY.findById(id).orElse(null);
        if (tempUser == null) {
            throw new UserNotFoundException("id:" + id);
        }

        return tempUser.getPosts();
    }

    //http://localhost:8080/jpa/users/101/posts
    @GetMapping("/{id}/posts/{post_id}")
    public EntityModel<Post> getPostByUserId(@PathVariable("id") Integer id,
                                      @PathVariable("post_id") Integer postId) {

        User tempUser = USER_REPOSITORY.findById(id).orElse(null);
        if (tempUser == null) {
            throw new UserNotFoundException("id:" + id);
        }

        List<Post> postList =  tempUser.getPosts();
        Optional<Post> tempPost = postList.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst();

        if(tempPost.isPresent()) {
            EntityModel<Post> postEntityModel = EntityModel.of(tempPost.get());

            WebMvcLinkBuilder link =
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserJpaRestController.class).getPostsByUserId(id));
            postEntityModel.add(link.withRel("all-posts-for-user-with-id-" + id));

            return postEntityModel;
        } else {
            throw new PostNotFoundException("postId:" + postId);
        }
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> createPostForUserById(@PathVariable Integer id,
                                                      @Valid @RequestBody Post post) {

        User tempUser = USER_REPOSITORY.findById(id).orElse(null);
        if (tempUser == null) {
            throw new UserNotFoundException("id:" + id);
        }

        post.setUser(tempUser);
        Post storedPost = POST_REPOSITORY.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedPost.getId())
                .toUri();

        return ResponseEntity.created(location).body(storedPost);
    }



}
