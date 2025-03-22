package com.example.waterbus.controller;

import com.example.waterbus.dto.StationRequestDTO;
import com.example.waterbus.entity.Station;
import com.example.waterbus.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    private  StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public List<Station> getAllStations(){
        return stationService.getAllStations();
    }

    @PostMapping
    Station addStation(@RequestBody StationRequestDTO dto){
        return stationService.addStation(dto);
    }
}
