package com.application.api.installment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://installments-v2.vercel.app", "http://localhost:3000")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH");
    }
}
