package com.example.helloui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    private static final Logger logger = LoggerFactory.getLogger(HelloRestController.class);

    private final HelloService helloService;

    public HelloRestController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/rest")
    public String hello() {
        logger.info("RestController hello() started.");
        HelloResource helloResource = helloService.hello();
        String message = helloResource.getMessage();
        logger.info("RestController hello() finished.");
        return message;
    }
}
