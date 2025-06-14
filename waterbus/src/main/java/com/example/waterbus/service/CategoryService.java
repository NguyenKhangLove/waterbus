package com.example.waterbus.service;

import com.example.waterbus.dto.res.CategoryWithPriceDto;
import com.example.waterbus.entity.Category;
import com.example.waterbus.entity.TicketPrice;
import com.example.waterbus.repository.CategoryRepository;
import com.example.waterbus.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private TicketPriceRepository priceRepo;

    public List<CategoryWithPriceDto> getAllWithPrices() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryWithPriceDto> result = new ArrayList<>();

        for (Category cat : categories) {
            TicketPrice latestPrice = priceRepo.findTopByCategoryOrderByCreatedDateDesc(cat);

            CategoryWithPriceDto dto = new CategoryWithPriceDto();
            dto.setIdCategory(cat.getIdCategory());
            dto.setDescription(cat.getDescription());
            dto.setStatus(cat.getStatus());

            if (latestPrice != null) {
                dto.setPrice(latestPrice.getPrice());
                dto.setCreatedDate(latestPrice.getCreatedDate());
            }

            result.add(dto);
        }

        return result;
    }

    public Category createCategory(String description, String status) {
        Category category = new Category();
        category.setDescription(description);
        category.setStatus(status);
        return categoryRepo.save(category);
    }

    public void createPrice(Category category, Double price) {
        TicketPrice ticketPrice = new TicketPrice();
        ticketPrice.setCategory(category);
        ticketPrice.setPrice(price);
        ticketPrice.setCreatedDate(LocalDate.now());
        priceRepo.save(ticketPrice);
    }
}
