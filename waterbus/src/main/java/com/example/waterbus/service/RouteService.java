package com.example.waterbus.service;

import com.example.waterbus.domain.Route;
import com.example.waterbus.repository.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tuyến đường với ID: " + id));
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

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
}
