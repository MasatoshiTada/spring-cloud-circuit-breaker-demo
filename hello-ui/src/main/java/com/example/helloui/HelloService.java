package com.example.helloui;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;

    public HelloService(RestTemplate restTemplate, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("helloApi");
    }

    public HelloResource hello() {
        logger.info("Service hello() started.");
        String apiUrl = "http://localhost:8090/hello"; // URLは、本来はEurekaなどのService Discoveryで取得する
        CheckedFunction0<HelloResource> decorateSupplier =
                CircuitBreaker.decorateCheckedSupplier(circuitBreaker,
                        () -> restTemplate.getForObject(apiUrl, HelloResource.class));
        Try<HelloResource> result = Try.of(decorateSupplier)
//                .onFailure(this::onFailureHello) // 失敗時のイベント処理
                .recover(this::recoverHello); // 失敗時の代替処理
        HelloResource helloResource = result.get();
        logger.info("Service hello() finished. Circuit = {}", circuitBreaker.getState());
        return helloResource;
    }

    private void onFailureHello(Throwable throwable) {
        logger.error("Error in Service: ", throwable);
    }

    private HelloResource recoverHello(Throwable throwable) {
        logger.error("Recover in Service");
        return new HelloResource("default");
    }
}
