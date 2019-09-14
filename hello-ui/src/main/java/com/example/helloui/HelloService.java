package com.example.helloui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    private final RestTemplate restTemplate;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public HelloService(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public HelloResource hello() {
        logger.info("Service hello() started.");
        String apiUrl = "http://localhost:8090/hello";
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("helloApi");
        HelloResource helloResource = circuitBreaker.run(
                () -> restTemplate.getForObject(apiUrl, HelloResource.class),
                throwable -> {
                    logger.error("Error in Service: ", throwable);
                    return new HelloResource("default message");
                }
        );
        logger.info("Service hello() finished.");
        return helloResource;
    }
}
