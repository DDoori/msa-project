package com.example.productservice.dto;

import com.example.productservice.domain.Product;
import lombok.Getter;
import java.util.UUID;

@Getter
public class ProductResponse {
    private UUID id;
    private UUID providerId;
    private String name;
    private int stock;

    private ProductResponse(Product product){
        this.id = product.getId();
        this.providerId = product.getProviderId();
        this.name = product.getName();
        this.stock = product.getStock();
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product);
    }
}
