package com.example.helloui;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class HelloUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloUiApplication.class, args);
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(1))
                .cancelRunningFuture(false) // TODO これ何？
                .build();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInClosedState(2)
                .ringBufferSizeInHalfOpenState(1)
                .build();
        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(timeLimiterConfig)
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .build()
        );
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public FilterRegistrationBean loggingFilter() {
        LoggingFilter loggingFilter = new LoggingFilter();
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>(loggingFilter);
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}

