package com.moldavets.microservices.currency_exchange_service.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
//    @CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodedResponse"
    @Bulkhead(name = "default")
//    @RateLimiter(name = "default")
    public String sampleApi() {
        logger.info("Sample API call");
//        ResponseEntity<String> restTemplate =
//                new RestTemplate().getForEntity("http://localhost:8080/some-info", String.class);
        return "Sample API";
    }

    private String hardcodedResponse (Exception e) {
        return "Hardcoded response";
    }

}
