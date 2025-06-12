package com.example.waterbus.repository;

import com.example.waterbus.entity.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketPriceRepository extends JpaRepository<TicketPrice,Long> {
    Optional<TicketPrice> findByCategory_IdCategory(Long idCategory);
    Optional<TicketPrice> findByIdPrice(Long idPrice);

    @Query("SELECT tp FROM TicketPrice tp WHERE tp.category.idCategory = :categoryId ORDER BY tp.createdDate DESC")
    List<TicketPrice> findLatestPriceByCategoryId(@Param("categoryId") Long categoryId);
}
