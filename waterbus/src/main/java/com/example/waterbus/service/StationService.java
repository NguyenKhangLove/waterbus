package com.example.waterbus.service;

import com.example.waterbus.model.req.StationReq;
import com.example.waterbus.domain.Station;
import com.example.waterbus.exception.StationNotFoundException;
import com.example.waterbus.repository.StationRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class StationService {
    private  StationRepository stationRepository;

    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
    }

    public Optional<Station> updateStation(Long id, StationReq dto) {
        return stationRepository.findById(id).map(station -> {
            station.setName(dto.getName());
            station.setAddress(dto.getAddress());
            station.setOrderNumber(dto.getOrderNumber());
            station.setStatus(dto.getStatus());
            return stationRepository.save(station);
        });
    }

    public Station addStation(StationReq dto) {
        System.out.println("Received DTO: " + dto);

        Station station = new Station();
        station.setName(dto.getName());
        station.setAddress(dto.getAddress());
        station.setOrderNumber(dto.getOrderNumber());
        station.setStatus(dto.getStatus());

        return stationRepository.save(station);
    }

    public boolean deleteStation(Long id) {
        if (stationRepository.existsById(id)) {
            stationRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
