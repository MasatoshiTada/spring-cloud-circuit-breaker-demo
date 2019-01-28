package com.example.helloui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/")
    public String hello(Model model) {
        logger.info("Controller hello() started.");
        HelloResource helloResource = helloService.hello();
        model.addAttribute("hello", helloResource);
        logger.info("Controller hello() finished.");
        return "index";
    }
}
