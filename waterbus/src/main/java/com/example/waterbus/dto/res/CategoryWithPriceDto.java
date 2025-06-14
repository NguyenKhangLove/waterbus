package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryWithPriceDto {
    private Long idCategory;
    private String description;
    private String status;
    private Double price;
    private LocalDate createdDate;
}

