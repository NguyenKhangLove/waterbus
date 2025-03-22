package com.example.waterbus.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationRequestDTO {
    private String name;
    private String address;
    private Integer orderNumber;
    private Integer  status;
}