package com.example.waterbus.service;

import com.example.waterbus.dto.req.TicketPriceDTO;
import com.example.waterbus.entity.Category;
import com.example.waterbus.entity.TicketPrice;
import com.example.waterbus.dto.res.TicketPriceRes;
import com.example.waterbus.repository.CategoryRepository;
import com.example.waterbus.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TicketPriceService {
    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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


    public Double getLatestPriceByCategoryId(Long categoryId) {
        List<TicketPrice> prices = ticketPriceRepository.findLatestPriceByCategoryId(categoryId);
        if (prices.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy giá cho category có id: " + categoryId);
        }
        return prices.get(0).getPrice();
    }

    public List<TicketPriceDTO> getAllPrices() {
        return ticketPriceRepository.findAll().stream().map(tp -> {
            TicketPriceDTO dto = new TicketPriceDTO();
            dto.setIdPrice(tp.getIdPrice());
            dto.setCategoryId(tp.getCategory().getIdCategory());
            dto.setPrice(tp.getPrice());
            dto.setCreatedDate(tp.getCreatedDate());
            return dto;
        }).collect(Collectors.toList());
    }

    public TicketPriceDTO addTicketPrice(TicketPriceDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy category"));

        TicketPrice tp = new TicketPrice();
        tp.setCategory(category);
        tp.setPrice(dto.getPrice());
        tp.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : LocalDate.now());

        TicketPrice saved = ticketPriceRepository.save(tp);

        dto.setIdPrice(saved.getIdPrice());
        dto.setCreatedDate(saved.getCreatedDate());
        return dto;
    }
}
