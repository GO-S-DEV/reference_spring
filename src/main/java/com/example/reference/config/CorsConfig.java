package com.example.reference.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 개발단계 모두 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지
        config.addAllowedOriginPattern("*"); // 모든 ip에 응답을 허용 -> 나중에는 특정 ip만 허용하도록 변경
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용
        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 허용
        source.registerCorsConfiguration("/api/**", config); // api로 들어오는 모든 요청에 대해서 위의 설정을 적용
        return new org.springframework.web.filter.CorsFilter(source);
    }


}
