package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRes {
    private Long id;
    private LocalTime departureTime;
    private String startStation;
    private String endStation;
    private Long routeId;
    private Long shipId;
    private String status;
    private LocalTime startTime;
    private LocalTime endTime;
}
