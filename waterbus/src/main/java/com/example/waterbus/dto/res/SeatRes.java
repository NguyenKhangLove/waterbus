package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatRes {
    private Long idSeat;
    private String seatNumber;
    private Long shipId;
}
