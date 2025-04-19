package com.example.waterbus.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingRes {
    private Long ticketId;
    private Double totalPrice; // Số tiền cần thanh toán
    private String qrCode; // Mã QR dạng Base64
    private String tempId; // Mã tạm để xác nhận
}
