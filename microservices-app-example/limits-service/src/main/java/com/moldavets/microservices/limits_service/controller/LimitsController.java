package com.moldavets.microservices.limits_service.controller;

import com.moldavets.microservices.limits_service.config.Configuration;
import com.moldavets.microservices.limits_service.model.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limits")
public class LimitsController {

    private Configuration config;

    @Autowired
    public LimitsController(Configuration config) {
        this.config = config;
    }

    @GetMapping("/")
    public Limit retrieveLimits() {
        return new Limit(config.getMinimum(), config.getMaximum());
    }

}
