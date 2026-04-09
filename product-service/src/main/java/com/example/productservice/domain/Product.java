package com.example.productservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="products")
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "product_provider_id", updatable = false, nullable = false)
    private UUID providerId;

    @Column(name = "product_name", updatable = false, nullable = false)
    private String name;

    @Column(name = "product_stock", updatable = false, nullable = false)
    private int stock;

    @Builder
    public Product(String name, UUID providerId, int stock){
        this.name = name;
        this.providerId = providerId;
        this.stock = stock;
    }
}
