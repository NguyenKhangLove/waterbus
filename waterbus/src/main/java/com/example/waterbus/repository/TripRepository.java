package com.example.waterbus.repository;

import com.example.waterbus.domain.Ship;
import com.example.waterbus.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsByShipAndDepartureDateAndDepartureTime(Ship ship, LocalDate departureDate, LocalTime departureTime);
}

