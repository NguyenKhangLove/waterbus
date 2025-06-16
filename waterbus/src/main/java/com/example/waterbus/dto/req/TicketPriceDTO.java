package com.example.waterbus.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor

public class TicketPriceDTO {
    private Long idPrice;
    private Long categoryId;
    private Double price;
    private LocalDate createdDate;

    // Constructors, Getters, Setters
}

