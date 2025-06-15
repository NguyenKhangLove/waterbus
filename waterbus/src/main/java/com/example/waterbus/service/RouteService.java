package com.example.waterbus.service;

import com.example.waterbus.dto.res.RouteDTO;
import com.example.waterbus.entity.Route;
import com.example.waterbus.entity.Station;
import com.example.waterbus.dto.res.RouteInfoRes;
import com.example.waterbus.repository.RouteRepository;
import com.example.waterbus.repository.StationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    @Autowired
    private  RouteRepository routeRepository;
    @Autowired
    private StationRepository stationRepository;

    public Route updateRoute(Long id, Route updatedRoute) {
        return routeRepository.findById(id)
                .map(route -> {
                    route.setEndStationId(updatedRoute.getStartStationId());
                    route.setEndStationId(updatedRoute.getStartStationId());
                    return routeRepository.save(route);
                })
                .orElseThrow(() -> new EntityNotFoundException("Không tồn tại ID: " + id));
    }
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tồn tại ID: " + id);
        }
        routeRepository.deleteById(id);
    }

    public RouteInfoRes getRouteInfo(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Station startStation = stationRepository.findById(route.getStartStationId())
                .orElseThrow(() -> new RuntimeException("Start station not found"));

        Station endStation = stationRepository.findById(route.getEndStationId())
                .orElseThrow(() -> new RuntimeException("End station not found"));

        return new RouteInfoRes(startStation.getName(), endStation.getName());
    }

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream()
                .map(route -> new RouteDTO(route.getId(), route.getStartStationId(), route.getEndStationId()))
                .collect(Collectors.toList());
    }

    public Route createRoute(RouteDTO dto) {
        Route route = new Route();
        route.setStartStationId(dto.getStartStationId());
        route.setEndStationId(dto.getEndStationId());
        return routeRepository.save(route);
    }
}
