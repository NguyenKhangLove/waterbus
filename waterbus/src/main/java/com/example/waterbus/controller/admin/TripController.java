package com.example.waterbus.controller.admin;

import com.example.waterbus.entity.Route;
import com.example.waterbus.entity.Station;
import com.example.waterbus.entity.Trip;
import com.example.waterbus.dto.req.TripReq;
import com.example.waterbus.dto.res.TripRes;
import com.example.waterbus.repository.StationRepository;
import com.example.waterbus.repository.TripRepository;
import com.example.waterbus.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    private  TripRepository tripRepository;
    @Autowired
    private StationRepository stationRepository;



    public TripController(TripService tripService) {
        this.tripService = tripService;
    }



    @GetMapping
    public ResponseEntity<List<TripRes>> getTrips(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Trip> trips;

        if ((status == null || status.isBlank()) && date == null) {
            trips = tripRepository.findAll();
        } else if (status != null && date == null) {
            trips = tripRepository.findByStatus(status);
        } else if (status == null && date != null) {
            trips = tripRepository.findByDepartureDate(date);
        } else {
            trips = tripRepository.findByStatusAndDepartureDate(status, date);
        }

        List<TripRes> result = trips.stream().map(trip -> {
            Route route = trip.getRoute();
            String startStation = stationRepository.findById(route.getStartStationId())
                    .map(Station::getName).orElse("Unknown Start");
            String endStation = stationRepository.findById(route.getEndStationId())
                    .map(Station::getName).orElse("Unknown End");

            return new TripRes(
                    trip.getId(),
                    trip.getDepartureTime(),
                    startStation,
                    endStation,
                    route.getId(),
                    trip.getShip().getId(),
                    trip.getStatus(),
                    trip.getDepartureTime(),
                    trip.getDepartureTime().plusHours(2)
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }



    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody TripReq tripReq) {
        Trip createdTrip = tripService.createTrip(tripReq);
        return ResponseEntity.ok(createdTrip);
    }

    @PostMapping("/generate-daily")
    public ResponseEntity<String> generateDailyTrips(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            // Kiểm tra ngày không phải trong quá khứ
            if (date.isBefore(LocalDate.now())) {
                return ResponseEntity.badRequest().body("Không thể tạo chuyến cho ngày trong quá khứ");
            }

            // Thực hiện tạo chuyến
            List<Trip> createdTrips = tripService.createDailyTrips(date);

            // Trả về thông báo text đơn giản
            return ResponseEntity.ok("Đã tạo thành công " + createdTrips.size() + " chuyến đi");
        } catch (IllegalStateException e) {
            // Xử lý các lỗi nghiệp vụ
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> cancelTrip(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String reason = body.get("reason");
        tripService.cancelTrip(id, reason);
        return ResponseEntity.ok("Hủy thành công");
    }


    @PatchMapping("/{id}/complete")
    public Trip completeTrip(@PathVariable Long id) {
        return tripService.completeTrip(id);
    }
}
