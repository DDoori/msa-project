package com.example.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductStockRequest {

    @Min(1)
    @NotNull
    private Integer stock;
}
