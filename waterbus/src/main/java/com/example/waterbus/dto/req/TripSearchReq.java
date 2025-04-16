package com.example.waterbus.dto.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TripSearchReq {
    private Long startStationId;
    private Long endStationId;
    private LocalDate departureDate;
}
