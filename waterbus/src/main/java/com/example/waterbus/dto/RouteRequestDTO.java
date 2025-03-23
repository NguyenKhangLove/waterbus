package com.example.waterbus.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RouteRequestDTO {
        private Long id;
        private Integer start_point_id;
        private Integer end_point_id;
}
