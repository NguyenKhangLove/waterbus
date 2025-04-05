package com.example.waterbus.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "route")
@Getter
@Setter
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer start_point_id;
    private Integer end_point_id;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RouteDetail> routeDetails;
    public List<RouteDetail> getRouteDetails() {
        return routeDetails;
    }
    public void setRouteDetails(List<RouteDetail> routeDetails) {
        this.routeDetails = routeDetails;
    }
}
