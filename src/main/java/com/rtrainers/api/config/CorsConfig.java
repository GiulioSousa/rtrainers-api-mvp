package com.rtrainers.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configProfessor = new CorsConfiguration();
        configProfessor.setAllowedOrigins(List.of("http://localhost:4200"));
        configProfessor.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configProfessor.setAllowedHeaders(List.of("*"));
        configProfessor.setAllowCredentials(true);

        CorsConfiguration configTotem = new CorsConfiguration();
        configTotem.setAllowedOrigins(List.of("http://localhost:4201"));
        configTotem.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        configTotem.setAllowedHeaders(List.of("*"));
        configTotem.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource fonte = new UrlBasedCorsConfigurationSource();
        fonte.registerCorsConfiguration("/professor/**", configProfessor);
        fonte.registerCorsConfiguration("/totem/**", configTotem);
        fonte.registerCorsConfiguration("/auth/**", configProfessor);

        return new CorsFilter(fonte);
    }
}