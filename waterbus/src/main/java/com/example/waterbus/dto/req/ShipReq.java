package com.example.waterbus.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipReq {
    private String name;
    private String registrationNumber;
    private Integer seatCapacity;
    private String status;
}

