package com.example.waterbus.service;


import com.example.waterbus.dto.req.AccountCreateReq;
import com.example.waterbus.entity.Account;
import com.example.waterbus.entity.Staff;
import com.example.waterbus.repository.AccountRepository;
import com.example.waterbus.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StaffRepository staffRepository;

    public void createAccountForStaff(AccountCreateReq req) {
        if (accountRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }

        Staff staff = staffRepository.findById(req.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên"));

        Account account = new Account();
        account.setUsername(req.getUsername());
        account.setPassword(req.getPassword()); // (bạn nên mã hoá ở đây)
        account.setRole("staff"); // mặc định
        account.setStaff(staff);

        accountRepository.save(account);
    }
}
