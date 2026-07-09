package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "user_limit")
@AllArgsConstructor
@NoArgsConstructor
public class UserLimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "current_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentLimit;

    @Column(name = "reserved", nullable = false, precision = 15, scale = 2)
    private BigDecimal reserved;
}

