package com.example.waterbus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "ROUTE_DETAIL")
@Getter
@Setter
public class RouteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetail;
    private Integer orderNumber;

    @ManyToOne
    private Route route;

    @ManyToOne
    private Station station;//phai la odernumber

    @Column(name = "departure_time")
    private LocalTime departureTime;
}
