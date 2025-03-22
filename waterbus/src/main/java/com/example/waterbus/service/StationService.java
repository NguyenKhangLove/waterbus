package com.example.waterbus.service;

import com.example.waterbus.dto.StationRequestDTO;
import com.example.waterbus.entity.Station;
import com.example.waterbus.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class StationService {
    private  StationRepository stationRepository;

    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public Station addStation(StationRequestDTO dto) {
        System.out.println("Received DTO: " + dto);

        Station station = new Station();
        station.setName(dto.getName());
        station.setAddress(dto.getAddress());
        station.setOrderNumber(dto.getOrderNumber());
        station.setStatus(dto.getStatus());

        return stationRepository.save(station);
    }
}
