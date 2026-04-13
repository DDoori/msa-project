package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping("/products/{id}/stock/decrease")
    void decreaseStock(@PathVariable UUID id, @RequestBody StockRequest request);

    @GetMapping("/products/provider")
    List<ProductResponse> getProductsByProvider(@RequestHeader("X-User-Id") String userId);

    @GetMapping("/products/{id}")
    ProductResponse getProduct(@PathVariable UUID id);

}