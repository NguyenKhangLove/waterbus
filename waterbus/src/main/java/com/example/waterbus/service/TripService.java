package com.example.waterbus.service;

import com.example.waterbus.domain.Route;
import com.example.waterbus.domain.Ship;
import com.example.waterbus.domain.Trip;
import com.example.waterbus.model.req.TripReq;
import com.example.waterbus.repository.RouteRepository;
import com.example.waterbus.repository.ShipRepository;
import com.example.waterbus.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public TripService(TripRepository tripRepository,
                       RouteRepository routeRepository,
                       ShipRepository shipRepository) {
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.shipRepository = shipRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
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
   //--------------Tim chuyen----------------------------------


    public List<Trip> findAvailableTrips(Long startStationId, Long endStationId, LocalDate departureDate) {
        LocalTime now = LocalTime.now();

        // 1. Lấy các route có cả 2 trạm theo đúng thứ tự
        List<Route> validRoutes = routeRepository.findRoutesWithOrderedStations(startStationId, endStationId);

        if (validRoutes.isEmpty()) return Collections.emptyList();

        // 2. Tìm các chuyến trong ngày
        List<Trip> trips = tripRepository.findByRouteInAndDepartureDate(validRoutes, departureDate);

        // 3. Nếu là hôm nay thì lọc giờ hiện tại
        if (departureDate.equals(LocalDate.now())) {
            trips = trips.stream()
                    .filter(t -> t.getDepartureTime().isAfter(now))
                    .collect(Collectors.toList());
        }
        return trips;
    }
}
