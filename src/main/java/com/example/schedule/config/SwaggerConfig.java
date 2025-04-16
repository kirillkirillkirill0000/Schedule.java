package com.example.schedule.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("University Schedule API")
                        .description("🚀 API for university schedule management\n\n" +
                                "🔹 Student groups\n" +
                                "🔹 Lesson scheduling")
                        .version("2.0")
                        .contact(new Contact()
                                .name("Kirill Romankov")
                                .url("https://github.com/kirillkirillkirill0000/Schedule.java.git")
                                .email("aoderet91@gmail.com")
                                .extensions(Map.of("github", Map.of(
                                        "url", "https://github.com/kirillkirillkirill0000/Schedule.java.git",
                                        "username", "kirillkirillkirill0000"))))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT"))
                        .extensions(Map.of("x-logo", Map.of(
                                "url", "https://img.icons8.com/fluency/96/000000/schedule.png",
                                "altText", "Schedule API Logo"))))
                .tags(List.of());
    }
}

//http://localhost:8080/swagger-ui.html