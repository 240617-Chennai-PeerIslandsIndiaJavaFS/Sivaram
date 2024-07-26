package com.example.quiz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public OpenAPI createopenApi(){
        return new OpenAPI().info(
                new Info().description("Api's for quiz app")
                        .title("Quiz app")
                        .version("0.1")
        );
    }
}
