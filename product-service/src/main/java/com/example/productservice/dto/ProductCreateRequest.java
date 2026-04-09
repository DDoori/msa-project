package com.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class ProductCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private UUID providerId;

    @NotBlank
    private int stock;
}
