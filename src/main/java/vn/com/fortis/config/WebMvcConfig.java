package vn.com.fortis.config;//package com.example.haus.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Value("${cors.allowed-origins}")
//    private String[] allowedOrigins;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedHeaders("*")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedOrigins(allowedOrigins)
//                .allowCredentials(true)
//                .maxAge(3600);
//
//        registry.addMapping("/auth/**")
//                .allowedHeaders("*")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedOrigins(allowedOrigins)
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}
