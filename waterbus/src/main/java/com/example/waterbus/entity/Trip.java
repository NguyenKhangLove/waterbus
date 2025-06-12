package com.example.waterbus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "trip")
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime departureTime;
    private LocalDate departureDate;
    @ManyToOne
    private Route route;
    @ManyToOne
    private Ship ship;
    private String status;
    private String cancelReason;
    private LocalTime cancelTime;

    public enum TripStatus {
        PENDING("Đang chờ"),
        IN_PROGRESS("Đang đi"),
        COMPLETED("Hoàn thành"),
        CANCELLED("Hủy");
        private final String status;
        TripStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }
    }
}

