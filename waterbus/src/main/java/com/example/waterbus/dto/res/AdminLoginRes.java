package com.example.waterbus.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLoginRes {
    private String message;
    private String token;
    private String role;
    private Long id;
}