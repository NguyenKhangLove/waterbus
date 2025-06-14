package com.example.waterbus.dto.req;

import com.example.waterbus.entity.Trip;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class TripReq {
    private LocalTime departureTime;
    private LocalDate departureDate;
    private Long routeId;  // Chỉ cần ID của Route
    private Long shipId;
}
