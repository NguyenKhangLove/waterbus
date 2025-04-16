package com.example.waterbus.dto.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchTripReq {
    private Long fromStationId;
    private Long toStationId;
    private LocalDate departureDate;
}
