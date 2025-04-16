package com.example.waterbus.controller.customer;


import com.example.waterbus.dto.req.AvailableSeatReq;
import com.example.waterbus.dto.req.TripSearchReq;
import com.example.waterbus.dto.res.AvailableSeatRes;
import com.example.waterbus.dto.res.TripSearchRes;
import com.example.waterbus.service.SeatService;
import com.example.waterbus.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerTripController {
    @Autowired
    private TripService tripService;
    @Autowired
    private SeatService seatService;

    @PostMapping("/search-trips")
    public ResponseEntity<List<TripSearchRes>> searchTrips(
            @RequestBody TripSearchReq request) {

        List<TripSearchRes> trips = tripService.searchTrips(request);
        return ResponseEntity.ok(trips);
    }

    @PostMapping("/available")
    public ResponseEntity<List<AvailableSeatRes>> getAvailableSeats(
            @RequestBody AvailableSeatReq request) {

        List<AvailableSeatRes> availableSeats = seatService.getAvailableSeats(request);
        return ResponseEntity.ok(availableSeats);
    }
}
