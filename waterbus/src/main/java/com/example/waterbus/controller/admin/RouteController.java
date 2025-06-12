package com.example.waterbus.controller.admin;

import com.example.waterbus.entity.Route;
import com.example.waterbus.dto.res.RouteInfoRes;
import com.example.waterbus.service.RouteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")

public class RouteController {
    private final RouteService routeService;
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<List<Route>> getRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteInfoRes> getRouteInfo(@PathVariable Long id) {
        RouteInfoRes info = routeService.getRouteInfo(id);
        return ResponseEntity.ok(info);
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        Route newRoute = routeService.createRoute(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody Route updatedRoute) {
        try {
            return ResponseEntity.ok(routeService.updateRoute(id, updatedRoute));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
