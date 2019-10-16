package com.serzh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetryConfiguration {

    @Bean
    public RetryListener retryListener() {
//        TODO return new RetryListener();
        return null;
    }
}
