package com.example.waterbus.dto.req;

import lombok.Data;

@Data
public class AvailableSeatReq {
    private Long tripId;
    private Integer startStationId;
    private Integer endStationId;

}
