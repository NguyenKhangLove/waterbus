package com.example.waterbus.service;

import com.example.waterbus.dto.request.AdminLoginRequest;
import com.example.waterbus.dto.response.AdminLoginResponse;
import com.example.waterbus.entity.Account;
import com.example.waterbus.exception.LoginException;
import com.example.waterbus.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AccountRepository accountRepository;

    public AdminLoginResponse login(AdminLoginRequest request) {

        Account account = accountRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new LoginException("Login fail") {
                });

        return AdminLoginResponse.builder().username(account.getUsername()).role(account.getRole()).build();
    }
}
