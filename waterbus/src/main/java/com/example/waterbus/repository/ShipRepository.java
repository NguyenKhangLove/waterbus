package com.example.waterbus.repository;

import com.example.waterbus.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository  extends JpaRepository<Ship,Long> {
    boolean existsByRegistrationNumber(String registrationNumber);
}
