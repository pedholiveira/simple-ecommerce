package com.tv.demo.simpleecommerce.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfiguration {

    @Bean
    public OpenAPI openApiConfiguration() {
        return new OpenAPI().info(new Info()
                .title("Simple Ecommerce")
                .version("1.0")
                .description("Manages the simple e-commerce platform")
        );
    }
}
