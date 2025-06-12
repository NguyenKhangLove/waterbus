package com.example.waterbus.service;

import com.example.waterbus.dto.req.AdminLoginReq;
import com.example.waterbus.dto.res.AdminLoginRes;
import com.example.waterbus.entity.Account;
import com.example.waterbus.exception.LoginException;
import com.example.waterbus.jwt.JwtUtil;
import com.example.waterbus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AdminAuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public AdminLoginRes login(AdminLoginReq request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new LoginException("Tên đăng nhập không tồn tại"));

        if (!account.getPassword().equals(request.getPassword())) {
            throw new LoginException("Mật khẩu không chính xác");
        }

        String token = jwtUtil.generateToken(account.getUsername(), account.getRole());

        return AdminLoginRes.builder()
                .message("Đăng nhập thành công!")
                .token(token)
                .role(account.getRole())
                .id(account.getStaff().getId())//chỗ này
                .build();

    }
}



