package com.example.waterbus.repository;

import com.example.waterbus.entity.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteDetailRepository extends JpaRepository<RouteDetail,Long> {

}
