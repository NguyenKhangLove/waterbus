package com.example.waterbus.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class TicketReq {
    private String fullname;
    private int birthYear;
    private String phone;
    private String email;
    private String nationality;

    private Long startStationId;
    private Long endStationId;
    private Long tripId;
    private Long staffId;
    private String paymentMethod;

    private List<Long> seatIds; // danh sách ghế chọn

    private List<TicketDetailReq> details; // có thể null nếu chỉ 1 ghế
}
