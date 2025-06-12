package com.example.waterbus.service;

import com.example.waterbus.entity.Seat;
import com.example.waterbus.dto.req.AvailableSeatReq;
import com.example.waterbus.dto.res.AvailableSeatRes;
import com.example.waterbus.dto.res.SeatRes;
import com.example.waterbus.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Transactional(readOnly = false)
    public List<AvailableSeatRes> getAvailableSeats(AvailableSeatReq request) {
        List<Object[]> results = seatRepository.getAvailableSeats(
                request.getTripId(),
                request.getStartStationId(),
                request.getEndStationId()
        );

        return results.stream().map(obj -> {
            AvailableSeatRes response = new AvailableSeatRes();
            response.setSeatId(((Number) obj[0]).longValue());
            response.setSeatNumber((String) obj[1]);
            return response;
        }).collect(Collectors.toList());
    }

    public List<SeatRes> getSeatsByShipId(Long shipId) {
        List<Seat> seats = seatRepository.findByShip_Id(shipId);
        return seats.stream()
                .map(seat -> new SeatRes(seat.getIdSeat(), seat.getSeatNumber(), seat.getShip().getId()))
                .collect(Collectors.toList());
    }
}
