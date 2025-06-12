package com.example.waterbus.entity;


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
    private String phone;
    private String email;
    private String gender;
    private String nationality;

}
