package com.example.waterbus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "Route_Detail")
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
    private Station station;

    @Column(name = "departure_time")
    private LocalTime departureTime;
}
