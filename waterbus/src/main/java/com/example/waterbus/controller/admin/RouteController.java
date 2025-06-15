package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.res.RouteDTO;
import com.example.waterbus.dto.res.RouteDetailDTO;
import com.example.waterbus.entity.Route;
import com.example.waterbus.dto.res.RouteInfoRes;
import com.example.waterbus.entity.RouteDetail;
import com.example.waterbus.repository.RouteDetailRepository;
import com.example.waterbus.service.RouteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")

public class RouteController {
    private final RouteService routeService;

    @Autowired
    private RouteDetailRepository routeDetailRepository;
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<RouteDTO> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @PostMapping
    public ResponseEntity<?> createRoute(@RequestBody RouteDTO dto) {
        Route saved = routeService.createRoute(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RouteDTO(saved.getId(), saved.getStartStationId(), saved.getEndStationId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteInfoRes> getRouteInfo(@PathVariable Long id) {
        RouteInfoRes info = routeService.getRouteInfo(id);
        return ResponseEntity.ok(info);
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

    @GetMapping("all")
    public List<RouteDetailDTO> getAllRouteDetails() {
        List<RouteDetail> list = routeDetailRepository.findAll();
        return list.stream().map(detail -> {
            String routeStr = (detail.getRoute().getId() == 1)
                    ? "Linh Đông → Bạch Đằng"
                    : "Bạch Đằng → Linh Đông";

            return new RouteDetailDTO(
                    detail.getIdDetail(),
                    routeStr,
                    detail.getStation().getName(),
                    detail.getDepartureTime().toString()
            );
        }).collect(Collectors.toList());
    }
}
