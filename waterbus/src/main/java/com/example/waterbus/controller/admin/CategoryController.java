package com.example.waterbus.controller.admin;


import com.example.waterbus.dto.req.CategoryWithPriceRequest;
import com.example.waterbus.dto.res.CategoryWithPriceDto;
import com.example.waterbus.entity.Category;
import com.example.waterbus.entity.TicketPrice;
import com.example.waterbus.repository.CategoryRepository;
import com.example.waterbus.repository.TicketPriceRepository;
import com.example.waterbus.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TicketPriceRepository priceRepo;



    @PostMapping
    public Category create(@RequestBody Category c) {
        return categoryRepo.save(c);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category c) {
        Category existing = categoryRepo.findById(id).orElseThrow();
        existing.setDescription(c.getDescription());
        existing.setStatus(c.getStatus());
        return categoryRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //  1. Lấy tất cả category + giá mới nhất
    @GetMapping("/categories-with-prices")
    public List<CategoryWithPriceDto> getAll() {
        return categoryService.getAllWithPrices();
    }

    // 2. Tạo mới category
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category c) {
        return ResponseEntity.ok(categoryRepo.save(c));
    }

    //  3. Tạo mới giá vé
    @PostMapping("/ticket-prices")
    public ResponseEntity<?> createTicketPrice(@RequestBody Map<String, Object> req) {
        Long categoryId = Long.valueOf(req.get("categoryId").toString());
        Double price = Double.valueOf(req.get("price").toString());

        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        TicketPrice ticketPrice = new TicketPrice();
        ticketPrice.setCategory(cat);
        ticketPrice.setPrice(price);
        ticketPrice.setCreatedDate(LocalDate.now());

        priceRepo.save(ticketPrice);
        return ResponseEntity.ok().build();
    }

    //  4. Cập nhật category
    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category c) {
        Category existing = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy"));

        existing.setDescription(c.getDescription());
        existing.setStatus(c.getStatus());

        return ResponseEntity.ok(categoryRepo.save(existing));
    }

    // 5. Xoá category
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (!categoryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //  6. Gộp thêm cả category + giá 1 lần
    @PostMapping("/categories-with-price")
    public ResponseEntity<?> createCategoryAndPrice(@RequestBody CategoryWithPriceRequest req) {
        Category saved = categoryService.createCategory(req.getDescription(), req.getStatus());
        categoryService.createPrice(saved, req.getPrice());
        return ResponseEntity.ok().build();
    }
}
