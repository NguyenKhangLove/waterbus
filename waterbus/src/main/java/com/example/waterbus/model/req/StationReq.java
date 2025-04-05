package com.example.waterbus.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationReq {
    private String name;
    private String address;
    private Integer orderNumber;
    private Integer  status;
}