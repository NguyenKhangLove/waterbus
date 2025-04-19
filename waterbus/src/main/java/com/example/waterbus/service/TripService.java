package com.example.waterbus.service;

import com.example.waterbus.domain.Route;
import com.example.waterbus.domain.Ship;
import com.example.waterbus.domain.Station;
import com.example.waterbus.domain.Trip;
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
import java.util.Collections;
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

    public List<Trip> createDailyTrips(LocalDate date) {
        List<Route> routes = routeRepository.findAll();
        List<Ship> ships = shipRepository.findAll();
        List<Trip> trips = new ArrayList<>();

        LocalTime[] departureTimes = {
                LocalTime.of(8, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(17, 0)
        };

        for (Route route : routes) {
            for (LocalTime time : departureTimes) {
                Ship ship = findAvailableShip(ships, date, time);
                if (ship != null) {
                    Trip trip = new Trip();
                    trip.setDepartureDate(date);
                    trip.setDepartureTime(time);
                    trip.setRoute(route);
                    trip.setShip(ship);
                    trip.setStatus(Trip.TripStatus.PENDING.getStatus());
                    trips.add(trip);
                }
            }
        }

        return tripRepository.saveAll(trips);
    }
    private Ship findAvailableShip(List<Ship> ships, LocalDate date, LocalTime time) {
        return ships.stream()
                .filter(ship -> !tripRepository.existsByShipAndDepartureDateAndDepartureTime(ship, date, time)) // Kiểm tra nếu chuyến đi không tồn tại
                .findFirst()
                .orElse(null); // Trả về tàu đầu ti ên nếu tìm thấy, nếu không trả về null
    }

    @Transactional(readOnly = false)
    public List<TripSearchRes> searchTrips(TripSearchReq request) {
        // Lấy giờ hiện tại
        LocalTime currentTime = LocalTime.now();

        // Gọi stored procedure
        List<Object[]> results = tripRepository.searchTripsByStationsAndDate(
                request.getStartStationId(),
                request.getEndStationId(),
                Date.valueOf(request.getDepartureDate()),
                Time.valueOf(currentTime)
        );

        // Chuyển đổi kết quả từ Object[] sang TripSearchResponse
        return results.stream()
                .map(this::mapToTripSearchResponse)
                .collect(Collectors.toList());
    }

    private TripSearchRes mapToTripSearchResponse(Object[] result) {
        TripSearchRes response = new TripSearchRes();
        response.setTripId((Long) result[0]);
        response.setDepartureDate(((Date) result[1]).toLocalDate());
        response.setStartStation((String) result[2]);
        response.setEndStation((String) result[3]);
        response.setRouteId((Long) result[4]);
        response.setStartTime(((Time) result[5]).toLocalTime());
        response.setEndTime(((Time) result[6]).toLocalTime());
        return response;
    }
}
