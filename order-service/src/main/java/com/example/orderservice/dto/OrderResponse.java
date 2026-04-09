package com.example.orderservice.dto;

import com.example.orderservice.domain.OrderStatus;
import com.example.orderservice.domain.Order;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderResponse {

    private UUID id;
    private UUID userId;
    private UUID productId;
    private int quantity;
    private OrderStatus status;

    private OrderResponse(Order order) {
        this.id = order.getId();
        this.userId = order.getUserId();
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.status = order.getStatus();
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order);
    }
}
