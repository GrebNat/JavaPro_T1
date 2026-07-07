package org.example.repository;

import org.example.entity.Product;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET balance = :newAmount WHERE id = :productId", nativeQuery = true)
    int updateProductBalance(Integer productId, BigDecimal newAmount);
}
