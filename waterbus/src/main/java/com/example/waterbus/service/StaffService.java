package com.example.waterbus.service;

import com.example.waterbus.domain.Staff;
import com.example.waterbus.dto.res.StaffRes;
import com.example.waterbus.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<StaffRes> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        List<StaffRes> dtoList = new ArrayList<>();

        for (Staff staff : staffList) {
            StaffRes dto = new StaffRes();
            dto.setId(staff.getId());
            dto.setFullName(staff.getFullName());
            dto.setIdentityNumber(staff.getIdentityNumber());
            dto.setBirthDate(staff.getBirthDate());
            dto.setGender(staff.getGender());
            dto.setPhone(staff.getPhone());
            dto.setEmail(staff.getEmail());
            dto.setAddress(staff.getAddress());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public StaffRes getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        StaffRes dto = new StaffRes();
        dto.setId(staff.getId());
        dto.setFullName(staff.getFullName());
        dto.setIdentityNumber(staff.getIdentityNumber());
        dto.setBirthDate(staff.getBirthDate());
        dto.setGender(staff.getGender());
        dto.setPhone(staff.getPhone());
        dto.setEmail(staff.getEmail());
        dto.setAddress(staff.getAddress());

        return dto;
    }
}

