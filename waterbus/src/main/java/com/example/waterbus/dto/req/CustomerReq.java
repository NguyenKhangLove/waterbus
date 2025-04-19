package com.example.waterbus.dto.req;

import lombok.Data;

@Data
public class CustomerReq {
    private String fullname;
    private Integer birthYear;
    private String phone;
    private String email;
    private String nationality;
}
