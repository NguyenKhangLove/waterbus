package com.example.waterbus.service;

import com.example.waterbus.domain.TicketPrice;
import com.example.waterbus.dto.res.TicketPriceRes;
import com.example.waterbus.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPriceService {
    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    // Lấy giá tiền khi truyền vào idCategory
    public TicketPriceRes getTicketPriceByCategory(Long idCategory) {
        TicketPrice ticketPrice = ticketPriceRepository.findByCategory_IdCategory(idCategory)
                .orElseThrow(() -> new RuntimeException("Ticket Price not found"));

        return new TicketPriceRes(ticketPrice.getPrice(), ticketPrice.getCreatedDate());
    }

    // Hoặc nếu lấy giá theo idPrice
    public TicketPriceRes getTicketPriceById(Long idPrice) {
        TicketPrice ticketPrice = ticketPriceRepository.findById(idPrice)
                .orElseThrow(() -> new RuntimeException("Ticket Price not found"));

        return new TicketPriceRes(ticketPrice.getPrice(), ticketPrice.getCreatedDate());
    }
}
