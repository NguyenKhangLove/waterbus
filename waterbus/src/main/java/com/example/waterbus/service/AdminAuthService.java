package com.example.waterbus.service;

import com.example.waterbus.model.req.AdminLoginReq;
import com.example.waterbus.model.res.AdminLoginRes;
import com.example.waterbus.domain.Account;
import com.example.waterbus.exception.LoginException;
import com.example.waterbus.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AccountRepository accountRepository;

    public AdminLoginRes login(AdminLoginReq request) {

        Account account = accountRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new LoginException("Login fail") {
                });

        return AdminLoginRes.builder().username(account.getUsername()).role(account.getRole()).build();
    }
}
