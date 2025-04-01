package com.example.waterbus.repository;

import com.example.waterbus.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  StaffRepository extends JpaRepository<Account,Long> {

}
