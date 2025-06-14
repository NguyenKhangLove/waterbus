package com.example.waterbus.repository;

import com.example.waterbus.entity.Account;
import com.example.waterbus.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);

    Account findByStaff(Staff staff);

    boolean existsByUsername(String username);
}
