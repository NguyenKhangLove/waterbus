package com.example.waterbus.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLoginRes {
    private String message;
    private String token;
    public AdminLoginRes(String message, String token) {
        this.message = message;
        this.token = token;
    }
    private String role;
    private Long id;
}