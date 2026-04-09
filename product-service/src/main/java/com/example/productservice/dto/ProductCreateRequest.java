package com.example.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductCreateRequest {
    @NotBlank
    private String name;

    @NotNull
    private UUID providerId;

    @Min(0)
    private int stock;}
