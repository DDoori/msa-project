package com.example.productservice.dto;

import com.example.productservice.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductCreateCommand {

    private String name;
    private UUID providerId;
    private int stock;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .providerId(providerId)
                .stock(stock)
                .build();
    }
}