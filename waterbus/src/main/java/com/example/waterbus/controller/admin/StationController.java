package com.example.waterbus.controller.admin;

import com.example.waterbus.entity.Station;
import com.example.waterbus.repository.StationRepository;
import com.example.waterbus.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    @Autowired
    private  StationService stationService;
    @Autowired
    private StationRepository stationRepository;


    @GetMapping
    public List<Station> getAllStations(){
        return stationService.getAllStations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> getStationById(@PathVariable Long id) {
        return ResponseEntity.ok(stationService.getStationById(id));
    }

    @PostMapping
    public Station addStation(@RequestBody Station station) {
        station.setStatus1("Đang hoạt động");

        // lưu trạm để JPA tự tạo ID
        Station saved = stationRepository.save(station);
        // gán status = id (sau khi đã có id)
        saved.setStatus(saved.getId().intValue());
        // lưu lại để cập nhật status
        return stationRepository.save(saved);
    }

    // PUT: Cập nhật trạm
    @PutMapping("/{id}")
    public Station updateStation(@PathVariable Long id, @RequestBody Station updated) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạm"));
        station.setName(updated.getName());
        station.setAddress(updated.getAddress());
        return stationRepository.save(station);
    }

    // DELETE (chuyển sang trạng thái ngừng hoạt động)
    @DeleteMapping("/{id}")
    public String deactivateStation(@PathVariable Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạm"));
        station.setStatus1("Ngừng hoạt động");
        stationRepository.save(station);
        return "Đã chuyển sang trạng thái ngừng hoạt động";
    }
}


