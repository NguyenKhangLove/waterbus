package com.example.waterbus.repository;

import com.example.waterbus.domain.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketPriceRepository extends JpaRepository<TicketPrice,Long> {
    Optional<TicketPrice> findByCategory_IdCategory(Long idCategory);
    Optional<TicketPrice> findByIdPrice(Long idPrice);
}
