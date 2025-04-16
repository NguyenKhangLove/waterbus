package com.example.waterbus.dto.res;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SearchTripRes {
    private Long tripId;
    private Long routeId;
    private LocalDate departureDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
