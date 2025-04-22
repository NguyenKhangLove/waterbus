package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfoRes {
    private String startStation;
    private String endStation;
}
