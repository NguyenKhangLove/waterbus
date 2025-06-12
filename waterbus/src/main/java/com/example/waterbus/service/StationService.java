package com.example.waterbus.service;

import com.example.waterbus.dto.req.StationReq;
import com.example.waterbus.entity.Station;
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

    public String getNameById(Long stationId) {
        return stationRepository.findById(stationId)
                .map(Station::getName)
                .orElse("Không tìm thấy");
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
    }

    public Optional<Station> updateStation(Long id, StationReq dto) {
        return stationRepository.findById(id).map(station -> {
            station.setName(dto.getName());
            station.setAddress(dto.getAddress());
            station.setStatus(dto.getStatus());
            return stationRepository.save(station);
        });
    }

    public Station addStation(StationReq dto) {
        Station station = new Station();
        station.setName(dto.getName());
        station.setAddress(dto.getAddress());
        station.setStatus(dto.getStatus());

        return stationRepository.save(station);
    }

    public boolean deleteStation(Long id) {//Trang thai ngung hoat dong
        if (stationRepository.existsById(id)) {
            stationRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
