package com.example.waterbus.controller.customer;

import com.example.waterbus.dto.req.AvailableSeatReq;
import com.example.waterbus.dto.req.TicketReq;
import com.example.waterbus.dto.req.TripSearchReq;
import com.example.waterbus.dto.res.*;
import com.example.waterbus.service.BookingService;
import com.example.waterbus.service.SeatService;
import com.example.waterbus.service.TripService;
import com.example.waterbus.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private BookingService bookingService;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

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

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody TicketReq req) {
        try {
            BookingRes res = bookingService.createPreviewBooking(req);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi tạo đơn đặt vé: " + e.getMessage());
        }
    }

    @PostMapping("/confirmQrPayment/{tempId}")
    public ResponseEntity<?> confirmQrPayment(@PathVariable String tempId) {
        return bookingService.confirmQrPayment(tempId);
    }

}
