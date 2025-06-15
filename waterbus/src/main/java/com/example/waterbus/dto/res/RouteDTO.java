package com.example.waterbus.dto.res;

import lombok.Data;

@Data

public class RouteDTO {
    private Long id;
    private Long startStationId;
    private Long endStationId;

    // Constructors
    public RouteDTO() {}
    public RouteDTO(Long id, Long startStationId, Long endStationId) {
        this.id = id;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
    }

}

