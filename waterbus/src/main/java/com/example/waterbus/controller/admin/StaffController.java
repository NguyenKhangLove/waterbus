package com.example.waterbus.controller.admin;

import com.example.waterbus.dto.req.AccountCreateReq;
import com.example.waterbus.entity.Staff;
import com.example.waterbus.dto.res.StaffRes;
import com.example.waterbus.service.AccountService;
import com.example.waterbus.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<StaffRes>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/{id}")
        public ResponseEntity<StaffRes> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PostMapping
    public ResponseEntity<String> addStaff(@RequestBody Staff staff) {
        staffService.addStaff(staff);
        return ResponseEntity.ok("Thêm thành công");
    }


    @PutMapping("/{id}")
    public Staff update(@PathVariable Long id, @RequestBody Staff updated) {
        return staffService.updateStaff(id, updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Xoá thành công");
    }

    @PostMapping("/create_acount")
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateReq req) {
        try {
            accountService.createAccountForStaff(req);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo tài khoản thành công");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
