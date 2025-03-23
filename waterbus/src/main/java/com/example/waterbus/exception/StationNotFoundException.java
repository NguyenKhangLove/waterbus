package com.example.waterbus.exception;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(Long id) {
        super("Không tìm thấy ID : " + id);
    }
}

