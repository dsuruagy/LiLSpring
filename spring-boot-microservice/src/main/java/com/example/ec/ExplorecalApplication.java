package com.example.ec;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Explore California API",
                description = "API definitions of the Explore California Microservice",
                version = "3.0.1"
        )
)
public class ExplorecalApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExplorecalApplication.class, args);
    }




}
