package com.example.waterbus.dto.req;

import lombok.Data;

@Data
public class AccountCreateReq {
    private String username;
    private String password;
    private Long staffId;
}

