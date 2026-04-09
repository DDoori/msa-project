package com.example.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderCreateRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID productId;

    @Min(1)
    private int quantity;
}
