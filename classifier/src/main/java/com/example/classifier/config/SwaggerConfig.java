package com.example.classifier.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Classifier API")
                        .version("1.0")
                        .description("Gender classification API"))
                .servers(List.of(
                        new Server().url("https://classifierapi.up.railway.app")
                ));
    }
}