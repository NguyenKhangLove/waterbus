package com.example.waterbus.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

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
}
