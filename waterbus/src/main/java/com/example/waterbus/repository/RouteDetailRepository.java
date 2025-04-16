package com.example.waterbus.repository;

import com.example.waterbus.domain.RouteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteDetailRepository extends JpaRepository<RouteDetail,Long> {

}
