package com.example.waterbus.service;

import com.example.waterbus.model.req.AdminLoginReq;
import com.example.waterbus.model.res.AdminLoginRes;
import com.example.waterbus.domain.Account;
import com.example.waterbus.exception.LoginException;
import com.example.waterbus.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
public class AdminAuthService {

    private final AccountRepository accountRepository;

    public AdminAuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AdminLoginRes login(AdminLoginReq request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new LoginException("Tên đăng nhập không tồn tại"));

        if (!account.getPassword().equals(request.getPassword())) {
            throw new LoginException("Mật khẩu không chính xác");
        }
        return AdminLoginRes.builder()
                .username(account.getUsername())
                .role(account.getRole())
                .build();
    }
}

