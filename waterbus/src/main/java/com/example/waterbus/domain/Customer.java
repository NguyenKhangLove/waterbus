package com.example.waterbus.domain;


import com.example.waterbus.dto.req.CustomerReq;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private Integer birthYear;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    private String gender;
    private String nationality;

}
