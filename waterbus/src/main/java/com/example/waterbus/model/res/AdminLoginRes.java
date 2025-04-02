package com.example.waterbus.model.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLoginRes {
    private String username;
    private String role;
}