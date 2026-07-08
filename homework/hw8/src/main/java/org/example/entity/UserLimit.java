package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_limit")
@AllArgsConstructor
public class UserLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "current_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentLimit;

    @Column(name = "reserved", nullable = false, precision = 15, scale = 2)
    private BigDecimal reserved;

    public UserLimit() {

    }
}

