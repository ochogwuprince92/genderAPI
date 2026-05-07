package com.example.classifier.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi classifierApi(){
        return GroupedOpenApi.builder()
                .group("classifier")
                .pathsToMatch("/*")
                .build();
    }
}
