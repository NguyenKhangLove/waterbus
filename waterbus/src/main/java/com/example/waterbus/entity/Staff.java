package com.example.waterbus.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String identityNumber;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
}
