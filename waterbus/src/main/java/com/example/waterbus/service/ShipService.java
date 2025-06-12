package com.example.waterbus.service;

import com.example.waterbus.exception.ShipException;
import com.example.waterbus.dto.req.ShipReq;
import com.example.waterbus.entity.Ship;
import com.example.waterbus.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

    public Ship updateShip(Long id, Ship updatedShip) {
        return shipRepository.findById(id).map(ship -> {
            ship.setName(updatedShip.getName());
            ship.setRegistrationNumber(updatedShip.getRegistrationNumber());
            ship.setSeatCapacity(updatedShip.getSeatCapacity());
            ship.setStatus(updatedShip.getStatus());
            return shipRepository.save(ship);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy tàu có ID: " + id));
    }





}


