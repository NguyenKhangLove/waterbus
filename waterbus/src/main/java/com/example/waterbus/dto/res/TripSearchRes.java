package com.example.waterbus.dto.res;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TripSearchRes {
    private Long tripId;
    private Long shipId;
    private LocalDate departureDate;
    private String startStation;
    private String endStation;
    private Long routeId;
    private LocalTime startTime;
    private LocalTime endTime;
}
