package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.res.StaffRes;
import com.example.waterbus.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public ResponseEntity<List<StaffRes>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/{id}")
        public ResponseEntity<StaffRes> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

}
