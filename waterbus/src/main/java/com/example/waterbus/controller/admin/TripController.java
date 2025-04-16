package com.example.waterbus.controller.admin;

import com.example.waterbus.domain.Trip;
import com.example.waterbus.dto.req.TripReq;
import com.example.waterbus.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody TripReq tripReq) {
        Trip createdTrip = tripService.createTrip(tripReq);
        return ResponseEntity.ok(createdTrip);
    }

    @PostMapping("/generate-daily")
    public ResponseEntity<List<Trip>> generateDailyTrips(@RequestParam LocalDate date) {
        return ResponseEntity.ok(tripService.createDailyTrips(date));
    }
}
