package com.example.waterbus.repository;

import com.example.waterbus.entity.Ship;
import com.example.waterbus.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    // Kiểm tra đã tồn tại chuyến nào cho ngày này chưa
    boolean existsByDepartureDate(LocalDate departureDate);

    // Kiểm tra tàu đã có chuyến trong ngày cụ thể chưa
    boolean existsByShipAndDepartureDate(Ship ship, LocalDate departureDate);

    @Procedure(procedureName = "sp_search_trip_by_station_and_date")
    List<Object[]> searchTripsByStationsAndDate(
            @Param("p_start_station_id") Long startStationId,
            @Param("p_end_station_id") Long endStationId,
            @Param("p_departure_date") LocalDate departureDate,
            @Param("p_time") Time currentTime);
    List<Trip> findByStatus(String status);

    // Tìm theo khoảng thời gian (1 ngày)
    List<Trip> findByDepartureDate(LocalDate departureDate);

    List<Trip> findByStatusAndDepartureDate(String status, LocalDate departureDate);
}

