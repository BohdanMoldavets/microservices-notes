package com.moldavets.restful_web_services.dao;

import com.moldavets.restful_web_services.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
