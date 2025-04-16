package com.example.waterbus.dto.req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RouteReq {
        private Long id;
        private Integer start_point_id;
        private Integer end_point_id;
}
