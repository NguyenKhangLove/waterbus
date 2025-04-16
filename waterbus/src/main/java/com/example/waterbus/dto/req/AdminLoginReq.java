package com.example.waterbus.dto.req;

import lombok.Data;

@Data
public class AdminLoginReq {
    private String username;
    private String password;
}