package com.example.waterbus.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class TicketReq {
    private Long tripId;
    private String departureDate;
    private Integer startStationId;
    private String startStationName;
    private Integer endStationId;
    private String endStationName;
    private String departureTime;
    private String arrivalTime;
    private List<String> seats;
    private CustomerReq customer;
    private List<TicketDetailReq> ticketDetails;
}
