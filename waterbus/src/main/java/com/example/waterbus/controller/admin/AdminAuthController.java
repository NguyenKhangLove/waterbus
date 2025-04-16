package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.req.AdminLoginReq;
import com.example.waterbus.dto.res.AdminLoginRes;
import com.example.waterbus.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginRes> login(@RequestBody AdminLoginReq request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }
}
