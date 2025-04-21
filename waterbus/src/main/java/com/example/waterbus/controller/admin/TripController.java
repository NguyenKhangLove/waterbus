package com.example.waterbus.controller.admin;

import com.example.waterbus.domain.Trip;
import com.example.waterbus.dto.req.TripReq;
import com.example.waterbus.dto.res.TripRes;
import com.example.waterbus.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TripRes>> getTripsByStatus(@PathVariable String status) {
        List<TripRes> trips = tripService.getTripsByStatus(status);
        return ResponseEntity.ok(trips);
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
}
