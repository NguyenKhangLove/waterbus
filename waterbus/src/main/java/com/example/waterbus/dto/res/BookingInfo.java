package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingInfo {
    private Long ticketId;
    private Double totalPrice;
}
