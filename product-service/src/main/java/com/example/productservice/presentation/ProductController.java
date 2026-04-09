package com.example.productservice.presentation;

import com.example.productservice.application.ProductService;
import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductCreateCommand;
import com.example.productservice.dto.ProductCreateRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.ProductStockRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        ProductCreateCommand command = ProductCreateCommand.builder()
                .name(request.getName())
                .providerId(request.getProviderId())
                .stock(request.getStock())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductResponse.from(productService.create(command)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ProductResponse.from(productService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.findAll(name, page, size).map(ProductResponse::from));
    }

    @PostMapping("/{id}/stock/decrease")
    public ResponseEntity<Void> decreaseStock(@PathVariable UUID id,
                                              @Valid @RequestBody ProductStockRequest request) {
        productService.decreaseStock(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/stock/increase")
    public ResponseEntity<Void> increaseStock(@PathVariable UUID id,
                                              @Valid @RequestBody ProductStockRequest request) {
        productService.increaseStock(id, request);
        return ResponseEntity.noContent().build();
    }
}
