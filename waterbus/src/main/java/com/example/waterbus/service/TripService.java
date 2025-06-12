package com.example.waterbus.service;

import com.example.waterbus.entity.Route;
import com.example.waterbus.entity.Ship;
import com.example.waterbus.entity.Station;
import com.example.waterbus.entity.Trip;
import com.example.waterbus.dto.req.TripReq;
import com.example.waterbus.dto.req.TripSearchReq;
import com.example.waterbus.dto.res.TripRes;
import com.example.waterbus.dto.res.TripSearchRes;
import com.example.waterbus.repository.RouteRepository;
import com.example.waterbus.repository.ShipRepository;
import com.example.waterbus.repository.StationRepository;
import com.example.waterbus.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private  RouteRepository routeRepository;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private StationRepository stationRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
    public List<TripRes> getTripsByStatus(String status) {
        List<Trip> trips = tripRepository.findByStatus(status);

        return trips.stream()
                .map(trip -> {
                    Route route = trip.getRoute();

                    // Lấy tên trạm từ startStationId và endStationId
                    String startStation = stationRepository.findById(route.getStartStationId())
                            .map(Station::getName)
                            .orElse("Unknown Start Station");

                    String endStation = stationRepository.findById(route.getEndStationId())
                            .map(Station::getName)
                            .orElse("Unknown End Station");

                    return new TripRes(
                            trip.getId(),
                            trip.getDepartureTime(),
                            startStation,
                            endStation,
                            route.getId(),
                            trip.getShip().getId(),
                            trip.getStatus(),
                            trip.getDepartureTime(),
                            trip.getDepartureTime().plusHours(2) // giả sử 2 tiếng
                    );
                })
                .collect(Collectors.toList());
    }
    public Trip createTrip(TripReq tripReq) {
        // Lấy Route từ DB
        Route route = routeRepository.findById(tripReq.getRouteId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường"));
        // Lấy Ship từ DB
        Ship ship = shipRepository.findById(tripReq.getShipId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tàu"));
        // Tạo đối tượng Trip mới
        Trip trip = new Trip();
        trip.setDepartureTime(tripReq.getDepartureTime());
        trip.setDepartureDate(tripReq.getDepartureDate());
        trip.setRoute(route);
        trip.setShip(ship);
        trip.setStatus(tripReq.getStatus() != null ? tripReq.getStatus() : Trip.TripStatus.PENDING.getStatus());

        return tripRepository.save(trip);
    }

    private boolean isShipAvailable(Ship ship, LocalDate date, LocalTime[] times) {
        // Kiểm tra tàu chưa có chuyến nào trong ngày
        return !tripRepository.existsByShipAndDepartureDate(ship, date);
    }

    public List<Trip> createDailyTrips(LocalDate date) {
        List<Route> routes = routeRepository.findAll();
        List<Ship> ships = shipRepository.findAll();
        List<Trip> trips = new ArrayList<>();

        // Danh sách các khung giờ khởi hành cố định
        LocalTime[] departureTimes = {
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(17, 0)
        };

        // Kiểm tra nếu đã có chuyến cho ngày này thì không tạo nữa
        if (tripRepository.existsByDepartureDate(date)) {
            throw new IllegalStateException("Đã tồn tại chuyến đi cho ngày " + date);        }

        // Đếm số lượng tàu cần thiết (2 tàu cho mỗi route)
        if (ships.size() < routes.size() * 2) {
            throw new RuntimeException("Không đủ tàu để tạo chuyến đi");
        }

        // Tạo chuyến đi cho mỗi route
        for (Route route : routes) {
            // Lấy 2 tàu đầu tiên chưa được sử dụng
            List<Ship> availableShips = ships.stream()
                    .filter(ship -> isShipAvailable(ship, date, departureTimes))
                    .limit(2)
                    .collect(Collectors.toList());

            if (availableShips.size() < 2) {
                throw new RuntimeException("Không đủ tàu khả dụng cho tuyến " + route.getId());
            }

            // Tạo 4 chuyến (cho 4 khung giờ) với 2 tàu luân phiên
            for (int i = 0; i < departureTimes.length; i++) {
                Trip trip = new Trip();
                trip.setDepartureDate(date);
                trip.setDepartureTime(departureTimes[i]);
                trip.setRoute(route);
                // Luân phiên sử dụng 2 tàu
                trip.setShip(availableShips.get(i % 2));
                trip.setStatus(Trip.TripStatus.PENDING.name()); // Lưu "PENDING"
                trips.add(trip);
            }
        }

        return tripRepository.saveAll(trips);
    }

    @Transactional(readOnly = false)
    public List<TripSearchRes> searchTrips(TripSearchReq request) {
        // Validate input
        if (request.getDepartureDate() == null) {
            throw new IllegalArgumentException("Departure date is required");
        }

        List<Object[]> results = tripRepository.searchTripsByStationsAndDate(
                request.getStartStationId(),
                request.getEndStationId(),
                request.getDepartureDate(), // giữ nguyên LocalDate
                Time.valueOf(LocalTime.now())
        );


        return results.stream()
                .map(this::mapToTripSearchResponse)
                .collect(Collectors.toList());
    }

    private TripSearchRes mapToTripSearchResponse(Object[] result) {
        TripSearchRes response = new TripSearchRes();
        response.setTripId(((Number) result[0]).longValue()); // Xử lý cả Integer và Long
        response.setShipId(((Number) result[1]).longValue());
        response.setDepartureDate(((Date) result[2]).toLocalDate());
        response.setStartStation((String) result[3]);
        response.setEndStation((String) result[4]);
        response.setRouteId(((Number) result[5]).longValue()); // Xử lý cả Integer và Long
        response.setStartTime(((Time) result[6]).toLocalTime());
        response.setEndTime(((Time) result[7]).toLocalTime());
        return response;
    }


    public Trip cancelTrip(Long id, String reason) {
        return tripRepository.findById(id).map(trip -> {
            trip.setStatus(Trip.TripStatus.CANCELLED.name()); // "CANCELLED"
            trip.setCancelReason(reason);
            trip.setCancelTime(LocalTime.now());
            return tripRepository.save(trip);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi với ID: " + id));
    }

    public Trip completeTrip(Long id) {
        return tripRepository.findById(id).map(trip -> {
            trip.setStatus(Trip.TripStatus.COMPLETED.name());
            return tripRepository.save(trip);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi với ID: " + id));
    }

}
