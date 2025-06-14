package com.example.waterbus.dto.req;

import lombok.Data;

@Data
public class CategoryWithPriceRequest {
    private String description;
    private String status;
    private Double price;
}
