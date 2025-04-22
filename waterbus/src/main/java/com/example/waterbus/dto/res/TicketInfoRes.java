package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfoRes {
    private String fullName;
    private Integer birthYear;
    private List<Long> seatIds;
}
