package com.example.waterbus.repository;

import com.example.waterbus.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    @Procedure(procedureName = "sp_get_available_seats")
    List<Object[]> getAvailableSeats(
            @Param("p_trip_id") Long tripId,
            @Param("p_start_station") Integer startStationId,
            @Param("p_end_station") Integer endStationId
    );

    List<Seat> findByShip_Id(Long shipId); // dùng ship.id

}
