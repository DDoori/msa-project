package com.example.orderservice.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ProductResponse {
    private UUID id;
    private UUID providerId;
    private String name;
    private int stock;
}