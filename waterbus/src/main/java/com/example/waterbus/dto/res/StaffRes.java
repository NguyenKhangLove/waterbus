package com.example.waterbus.dto.res;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffRes {
    private Long id;
    private String fullName;
    private String identityNumber;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
}
