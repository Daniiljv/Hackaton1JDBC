package com.jdbc.hacaton1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configuration() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hackaton#1")
                        .description("Web app for evaluating clothes")
                        .version("1.1.0")
                        .contact(new Contact().name("Daniil")
                        .email("k.daniil2023@gmail.com")));
    }

}
