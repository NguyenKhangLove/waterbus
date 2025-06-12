package com.example.waterbus.controller.admin;

import com.example.waterbus.entity.Ship;
import com.example.waterbus.dto.req.ShipReq;
import com.example.waterbus.dto.res.SeatRes;
import com.example.waterbus.service.SeatService;
import com.example.waterbus.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;
    @Autowired
    private SeatService seatService;

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody ShipReq request) {
        Ship newShip = shipService.createShip(request);
        return ResponseEntity.ok(newShip);
    }

    @GetMapping
    public ResponseEntity<List<Ship>> getAllShips() {
        List<Ship> ships = shipService.getAllShips();
        return ResponseEntity.ok(ships);
    }

    @GetMapping("/seat/{shipId}")
    public ResponseEntity<List<SeatRes>> getSeatsByShip(@PathVariable Long shipId) {
        List<SeatRes> seats = seatService.getSeatsByShipId(shipId);
        return ResponseEntity.ok(seats);
    }

    @DeleteMapping("/{id}")
    public void deleteShip(@PathVariable Long id) {
        shipService.deleteShip(id);
    }

    @PutMapping("/{id}")
    public Ship updateShip(@PathVariable Long id, @RequestBody Ship ship) {
        return shipService.updateShip(id, ship);
    }
}
