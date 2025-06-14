package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDetailDTO {
    private Long idDetail;
    private String route;
    private String station;
    private String departureTime;
}
