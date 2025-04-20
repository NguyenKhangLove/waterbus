package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevenueRes {
    private String label;
    private Double totalRevenue;
}
