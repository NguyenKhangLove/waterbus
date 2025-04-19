package com.example.waterbus;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Áp dụng cho tất cả các đường dẫn bắt đầu bằng /api/
                .allowedOrigins("http://127.0.0.1:5500")  // Cho phép truy cập từ frontend tại localhost:5500
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức HTTP được phép
                .allowedHeaders("*");  // Cho phép tất cả các header
    }
}
