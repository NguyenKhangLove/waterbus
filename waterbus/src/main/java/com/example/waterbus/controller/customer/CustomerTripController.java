package com.example.waterbus.controller.customer;


import com.example.waterbus.domain.Trip;
import com.example.waterbus.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/customer/trips")
public class CustomerTripController {
    private TripService tripService;

    public CustomerTripController(TripService tripService) {
        this.tripService = tripService;
    }
    @GetMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(
            @RequestParam Long startStationId,
            @RequestParam Long endStationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        List<Trip> trips = tripService.findAvailableTrips(startStationId, endStationId, departureDate);
        return ResponseEntity.ok(trips);
    }
}
