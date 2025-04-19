package com.example.waterbus.controller.admin;

import com.example.waterbus.domain.Ship;
import com.example.waterbus.dto.req.ShipReq;
import com.example.waterbus.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ships")
public class ShipController {
    @Autowired
    private ShipService shipService;

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody ShipReq request) {
        Ship newShip = shipService.createShip(request);
        return ResponseEntity.ok(newShip);
    }
}
