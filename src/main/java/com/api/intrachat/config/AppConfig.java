package com.api.intrachat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // aplica a todas las rutas
                        .allowedOriginPatterns("*") // permite todos los orígenes
                        .allowedMethods("*")        // permite todos los métodos (GET, POST, PUT, DELETE, etc.)
                        .allowedHeaders("*")        // permite todos los headers
                        .allowCredentials(true);    // permite credenciales (cookies, auth headers)
            }
        };
    }

}
