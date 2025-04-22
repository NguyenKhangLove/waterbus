package com.example.waterbus.dto.res;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketPriceRes {
    private Double price;
    private LocalDate createdDate;

    public TicketPriceRes(Double price, LocalDate createdDate) {
        this.price = price;
        this.createdDate = createdDate;
    }
}
