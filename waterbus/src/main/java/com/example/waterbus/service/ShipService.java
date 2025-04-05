package com.example.waterbus.service;

import com.example.waterbus.exception.ShipException;
import com.example.waterbus.model.req.ShipReq;
import com.example.waterbus.domain.Ship;
import com.example.waterbus.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipService {
    @Autowired
    private ShipRepository shipRepository;

    public Ship createShip(ShipReq request) {
        if (shipRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new ShipException("Biển số đã tồn tại");
        }

        Ship ship = new Ship();
        ship.setName(request.getName());
        ship.setRegistrationNumber(request.getRegistrationNumber());
        ship.setSeatCapacity(request.getSeatCapacity());
        ship.setStatus(request.getStatus());

        return shipRepository.save(ship);
    }




}


