package com.example.helloapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final Random random = new Random();

    @GetMapping("/hello")
    public HelloResource hello() {
        if (random.nextBoolean()) {
            logger.error("Error!");
            throw new RuntimeException("error");
        }
        logger.info("hello()");
        return new HelloResource("hello");
    }
}
