package com.example.waterbus.repository;

import com.example.waterbus.domain.Route;
import com.example.waterbus.domain.Ship;
import com.example.waterbus.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsByShipAndDepartureDateAndDepartureTime(Ship ship, LocalDate departureDate, LocalTime departureTime);

    @Procedure(procedureName = "sp_search_trip_by_station_and_date3")
    List<Object[]> searchTripsByStationsAndDate(
            @Param("p_start_station_id") Long startStationId,
            @Param("p_end_station_id") Long endStationId,
            @Param("p_departure_date") Date departureDate,
            @Param("p_time") Time currentTime);
    List<Trip> findByStatus(String status);
}

