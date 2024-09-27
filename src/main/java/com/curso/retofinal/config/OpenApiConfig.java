package com.curso.retofinal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig  {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Microservicio Reto Final")
                        .version("1.0")
                        .description("Este microservicio es el desarrollo de del reto final para el curso de " +
                                "programacion reactiva"));
    }
}