package com.moldavets.restful_web_services.dao;

import com.moldavets.restful_web_services.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
