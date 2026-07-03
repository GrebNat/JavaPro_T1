package org.example.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account", length = 255)
    private String account;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "product_type")
    private String productType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", balance=" + balance +
                ", productType='" + productType + '\'' +
                ", user=" + user +
                '}';
    }
}