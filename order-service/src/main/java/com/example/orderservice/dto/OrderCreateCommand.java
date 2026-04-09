package com.example.orderservice.dto;

import com.example.orderservice.domain.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderCreateCommand {

    private UUID userId;
    private UUID productId;
    private int quantity;

    public Order toEntity() {
        return Order.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}