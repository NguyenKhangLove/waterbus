package com.example.waterbus.repository;

import com.example.waterbus.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
    @Query("""
    SELECT DISTINCT rdStart.route FROM RouteDetail rdStart
    JOIN RouteDetail rdEnd ON rdEnd.route = rdStart.route
    WHERE rdStart.station.id = :startStationId
      AND rdEnd.station.id = :endStationId
      AND rdStart.orderNumber < rdEnd.orderNumber
""")
    List<Route> findRoutesWithOrderedStations(
            @Param("startStationId") Long startStationId,
            @Param("endStationId") Long endStationId
    );

    Optional<Route> findById(Long id);

}
